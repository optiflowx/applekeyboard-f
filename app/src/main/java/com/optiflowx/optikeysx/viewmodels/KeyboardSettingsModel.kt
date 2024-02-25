package com.optiflowx.optikeysx.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.Settings
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.google.firebase.auth.FirebaseAuth
import com.optiflowx.optikeysx.core.model.Response
import com.optiflowx.optikeysx.ime.recognizers.providers.Providers
import com.optiflowx.optikeysx.optikeysxPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class KeyboardSettingsModel : ScreenModel {
    val prefs by optikeysxPreferences()
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var recognizerSourceProviders: Providers

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

        prefs.modelsOrder.set(currentModels)
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

    fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String
    ) = screenModelScope.launch(Dispatchers.IO) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    fun sendEmailVerification() = screenModelScope.launch(Dispatchers.IO) {
        try {
            auth.currentUser?.sendEmailVerification()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ) = screenModelScope.launch(Dispatchers.IO) {
        try {
            auth.signInWithEmailAndPassword(email, password).await().also {
                auth.currentUser?.reload()?.await()
            }
            Response.Success(true)
            prefs.isAuthenticated.set(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    fun reloadFirebaseUser() = screenModelScope.launch(Dispatchers.IO) {
        try {
            auth.currentUser?.reload()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    fun signOut() = auth.signOut()


    fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }

        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}