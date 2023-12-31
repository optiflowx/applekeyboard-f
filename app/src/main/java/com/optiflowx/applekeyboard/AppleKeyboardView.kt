@file:Suppress("UNCHECKED_CAST")

package com.optiflowx.applekeyboard

import android.content.Context
import android.util.Log
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.ui.AppleKeyboardIMETheme
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import com.optiflowx.applekeyboard.views.KeyboardView

class AppleKeyboardView(context: Context) : AbstractComposeView(context) {

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        Log.d("AppleKeyboardView", "onViewAdded")
    }

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        Log.d("AppleKeyboardView", "onViewRemoved")
    }

    override fun onStartTemporaryDetach() {
        super.onStartTemporaryDetach()
        Log.d("AppleKeyboardView", "onStartTemporaryDetach")
    }

    override fun addOnAttachStateChangeListener(listener: OnAttachStateChangeListener?) {
        super.addOnAttachStateChangeListener(listener)
        Log.d("AppleKeyboardView", "addOnAttachStateChangeListener")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d("AppleKeyboardView", "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d("AppleKeyboardView", "onDetachedFromWindow")
    }

    @Composable
    override fun Content() {
        val width = LocalConfiguration.current.screenWidthDp
        val colorScheme = MaterialTheme.colorScheme
        val context = LocalContext.current
        val viewModel = viewModel<KeyboardViewModel>(
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(width, colorScheme, context) as T
                }
            }
        )

        val keyboardSize = viewModel.keyboardSize.observeAsState().value!!
        val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

        //Keyboard
        val keyboardHeight = keyboardSize.y.dp + bottomPadding
        val keyboardWidth = keyboardSize.x.dp

        AppleKeyboardIMETheme {
            Box(
                modifier = Modifier
                    .height(keyboardHeight)
                    .width(keyboardWidth)
            ) {
//                if(showPopup == true)  {
//                    LanguageSelectionPopup(keyboardHeight.value, keyboardWidth.value, viewModel)
//                }
                Card(
                    colors = cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.fillMaxSize()
                ) { KeyboardView(viewModel) }
            }
            isSystemInDarkTheme()
        }
    }
}