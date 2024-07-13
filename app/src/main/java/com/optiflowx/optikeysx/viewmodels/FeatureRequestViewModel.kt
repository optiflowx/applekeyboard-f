package com.optiflowx.optikeysx.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.google.firebase.firestore.FirebaseFirestore
import com.optiflowx.optikeysx.core.model.FeatureRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp

class FeatureRequestViewModel : ScreenModel {

    private val collectionId = "feature-requests"
    private val firestore = FirebaseFirestore.getInstance()

    private val _requests = MutableStateFlow(listOf<FeatureRequest>())
    val requests = _requests.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    val isLoading = MutableStateFlow(false)

    private enum class FeatureRequestStatus {
        Open,
        InProgress,
        Done,
        Declined
    }

    init {
        getFeatureRequests()
    }

    fun setContent(content: String) {
        _content.value = content
    }

    private fun getFeatureRequests() {
        isLoading.value = true

        screenModelScope.launch {
            firestore.collection(collectionId).get().addOnSuccessListener { res ->
                _requests.value = res.documents.map { document ->
                    val data = document.data
                    val request = FeatureRequest()
                    data?.let { request.fromMap(it) }
                    request
                }
            }.addOnFailureListener {
                Log.e("FeatureRequestViewModel", "Error getting documents: $it")
            }.await().apply {
                isLoading.value = false
            }
        }
    }

    fun submitFeatureRequest(context: Context, navigator: Navigator) {
        if (content.value.isEmpty()) {
            Toast.makeText(context, "Feature request cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val timestamp = Timestamp(System.currentTimeMillis())

        val request = hashMapOf(
            "content" to content.value,
            "status" to FeatureRequestStatus.Open.name,
            "time" to timestamp,
        )

        try {
            screenModelScope.launch(Dispatchers.Main) {
                firestore.collection(collectionId).add(request).await()

                _content.value = "".apply {
                    Toast.makeText(context, "Feature request submitted", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (e: Exception) {
            Log.e("FeatureRequestViewModel", "Error adding document: $e")
            Toast.makeText(context, "Error adding document: $e", Toast.LENGTH_SHORT).show()
        } finally {
            getFeatureRequests().apply {
                navigator.pop()
            }
        }
    }
}