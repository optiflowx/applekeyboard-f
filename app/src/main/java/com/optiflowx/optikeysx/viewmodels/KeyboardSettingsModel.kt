package com.optiflowx.optikeysx.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.model.AppUserData
import com.optiflowx.optikeysx.core.model.FirebaseUserData
import com.optiflowx.optikeysx.core.model.Response
import com.optiflowx.optikeysx.ime.recognizers.providers.Providers
import com.optiflowx.optikeysx.optikeysxPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class KeyboardSettingsModel : ScreenModel {
    val prefs by optikeysxPreferences()
    val loader = MutableLiveData(false)
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore(auth.app)
    private lateinit var recognizerSourceProviders: Providers

    init {
        db.persistentCacheIndexManager?.enableIndexAutoCreation()

        screenModelScope.launch(Dispatchers.IO) {
            getAuthState().collectLatest {
                if (it) {
                    prefs.isAuthenticated.set(true)
                    auth.currentUser?.reload()
                } else {
                    prefs.isAuthenticated.set(false)
                    resetLocalDatabase()
                }
            }
        }

        loadUserData()
    }

    fun loadUserData() {
        screenModelScope.launch(Dispatchers.IO) {
            getUserData().collectLatest {
                if (it.isPaid) {
                    if (it.isPremium) {
                        prefs.isPremium.set(true)
                    } else resetLocalDatabase()
                }
            }
        }
    }

    private fun resetLocalDatabase() {
        prefs.isPremium.set(false)
        prefs.isDotShortcut.set(false)
        prefs.isEnableAccents.set(false)
        prefs.isEnableMemoji.set(false)
        prefs.isAutoCorrect.set(false)
        prefs.isAutoCorrection.set(false)
        prefs.isPredictive.set(false)
        prefs.isVibrateOnKeypress.set(false)
        prefs.isSoundOnKeypress.set(false)
        prefs.autoSwitchIBackIME.set(false)
        prefs.keepLanguageModelInMemory.set(false)
        prefs.keyboardFontType.set(KeyboardFontType.Regular)
    }

    fun initRecognizerSourceProviders(context: Context) {
        recognizerSourceProviders = Providers(context)
    }

    fun reloadModels() {
        val currentModels = prefs.modelsOrder.get().toMutableList()
        val installedModels = recognizerSourceProviders.installedModels()
        currentModels.removeAll { it !in installedModels }
        for (model in installedModels) {
            if (model !in currentModels) {
                currentModels.add(model)
            }
        }

        screenModelScope.launch(Dispatchers.IO) {
            prefs.modelsOrder.set(currentModels)
        }
    }

    fun onAddKeyboard(context: Context) {
        val imId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.DEFAULT_INPUT_METHOD
        )

        val intent = Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS)
            .putExtra(Settings.EXTRA_INPUT_METHOD_ID, imId)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra(Intent.EXTRA_TITLE, "Add Keyboard")
        context.startActivity(intent);
    }

    fun signOut() = auth.signOut()

    fun firebaseSignUpWithEmailAndPassword(context: Context, user: AppUserData) {
        if (user.name.isNotEmpty() && user.email.isNotEmpty() && user.password.isNotEmpty()) {
            loader.postValue(true)

            screenModelScope.launch(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(user.email, user.password)
                    .addOnSuccessListener {
                        val docID = it.user?.uid

                        if (docID != null) {
                            addUserToDatabase(user, docID, context)
                        } else {
                            val error = Exception("User is null, Reverting all commits.")
                            onFailureHandler(error, context)
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Response.Failure(it)
                    }
                    .addOnCompleteListener {
                        loader.postValue(false)
                    }.await()
            }
        }
    }

    fun sendEmailVerification(context: Context) {
        try {
            screenModelScope.launch {
                auth.currentUser?.sendEmailVerification()?.await()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            Response.Failure(e)
        }
    }

    fun reloadFirebaseUser(context: Context) {
        screenModelScope.launch {
            auth.currentUser?.reload()?.addOnSuccessListener {
                Response.Success(true)
            }?.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                Response.Failure(it)
            }
        }
    }

    fun firebaseSignInWithEmailAndPassword(email: String, password: String, context: Context) {
        loader.postValue(true)
        screenModelScope.launch {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                reloadFirebaseUser(context)
                Response.Success(true)
            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                Response.Failure(it)
            }.addOnCompleteListener {
                loader.postValue(false)
            }.await()
        }
    }

    private fun onFailureHandler(error: Exception, context: Context) {
        Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()

        //Remove the already added user in authentication
        screenModelScope.launch(Dispatchers.IO) {
            auth.currentUser?.delete()?.await()
            prefs.isAuthenticated.set(false)
        }
    }

    private fun addUserToDatabase(user: AppUserData, docID: String, context: Context) {
        val userData = hashMapOf(
            "name" to user.name,
            "email" to user.email,
            "isPremium" to user.isPremium,
            "isPaid" to user.isPaid
        )

        screenModelScope.launch {
            db.collection("users").document(docID).set(userData)
                .addOnFailureListener { error ->
                    onFailureHandler(error, context)
                }
        }
    }

    private fun getUserId(): Flow<String> {
        return flow {
            auth.currentUser?.uid?.let {
                emit(it)
            }
        }
    }

    private fun getUserData() = callbackFlow {
        var data: FirebaseUserData

        getUserId().collect { userID ->
            val snapshot = db.collection("users").document(userID)

            snapshot.addSnapshotListener { value, error ->
                error?.let { e -> this.close(e) }

                value?.let { doc ->
                    data = FirebaseUserData(
                        name = doc.getString("name")!!,
                        email = doc.getString("email")!!,
                        isPremium = doc.getBoolean("isPremium") ?: false,
                        isPaid = doc.getBoolean("isPaid") ?: false,
                    )

                    this.trySend(data)
                }
            }
        }

        awaitClose { this.cancel() }
    }

    private fun getAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }

        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), auth.currentUser != null)
}