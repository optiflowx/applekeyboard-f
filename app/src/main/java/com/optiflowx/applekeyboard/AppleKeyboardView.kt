@file:Suppress("UNCHECKED_CAST")

package com.optiflowx.applekeyboard

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.global.KeyboardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AppleKeyboardView(context: Context) : AbstractComposeView(context) {
    @Composable
    override fun Content() {
        //ViewModel and Context
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val viewModel = viewModel<KeyboardViewModel>(
            key = "KeyboardViewModel",
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(context) as T
                }
            }
        )

        DisposableEffect(
            viewModel.soundIDT,
            viewModel.soundIDR,
            viewModel.soundIDS,
            viewModel.soundIDT
        ) {
            //Init soundID's on the first launch
            viewModel.soundIDT.value = viewModel.soundPool.load(context, R.raw.standard, 1)
            viewModel.soundIDD.value = viewModel.soundPool.load(context, R.raw.delete, 1)
            viewModel.soundIDS.value = viewModel.soundPool.load(context, R.raw.spacebar, 1)
            viewModel.soundIDR.value = viewModel.soundPool.load(context, R.raw.ret, 1)

            //Check if the sounds are loaded and update the value
            scope.launch(Dispatchers.IO) {
                viewModel.soundPool.setOnLoadCompleteListener { _, _, status ->
                    //If one of the sounds fail to load, this will be false
                    viewModel.isPoolLoaded.value = (status == 0)
                }
            }

            onDispose {
                //Unload the sounds and release the soundPool
                scope.launch(Dispatchers.IO) {
                    viewModel.soundPool.unload(viewModel.soundIDT.value)
                    viewModel.soundPool.unload(viewModel.soundIDD.value)
                    viewModel.soundPool.unload(viewModel.soundIDS.value)
                    viewModel.soundPool.unload(viewModel.soundIDR.value)
                }.invokeOnCompletion {
                    //Reset the values to defaults
                    viewModel.soundIDT.value = 0
                    viewModel.soundIDD.value = 0
                    viewModel.soundIDS.value = 0
                    viewModel.soundIDR.value = 0
                    viewModel.soundPool.release()
                    viewModel.isPoolLoaded.value = false
                }
            }
        }

        AppleKeyboardIMETheme {
            Box(Modifier.wrapContentSize()) {
                Card(
                    shape = RectangleShape,
                    colors = cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                ) { KeyboardView(viewModel) }
            }

            isSystemInDarkTheme()
        }
    }
}