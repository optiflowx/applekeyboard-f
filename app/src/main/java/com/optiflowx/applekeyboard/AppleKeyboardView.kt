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


class AppleKeyboardView(context: Context) : AbstractComposeView(context) {
    @Composable
    override fun Content() {
        //ViewModel and Context
        val context = LocalContext.current

        val viewModel = viewModel<KeyboardViewModel>(
            key = "KeyboardViewModel",
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return KeyboardViewModel(context) as T
                }
            }
        )

        AppleKeyboardIMETheme {
            Box(Modifier.wrapContentSize()) {
//                Image(
//                    painter = painterResource(
//                        if (isSystemInDarkTheme())
//                            R.drawable.bg_1
//                        else R.drawable.bg_2
//                    ),
//                    contentDescription = null,
//                    alignment = Alignment.Center,
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier.fillMaxSize(),
//                    alpha = 0.94f
//                )
                Card(
                    shape = RectangleShape,
                    colors = cardColors(
                        containerColor = MaterialTheme.colorScheme.background
//                            .copy(
//                            alpha = if(isSystemInDarkTheme()) 0.88f else 0.82f
//                        )
                    ),
                ) { KeyboardView(viewModel) }
            }

            isSystemInDarkTheme()
        }
    }
}