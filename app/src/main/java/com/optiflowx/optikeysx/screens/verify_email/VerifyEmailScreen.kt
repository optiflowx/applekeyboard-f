package com.optiflowx.optikeysx.screens.verify_email

import TopBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.optiflowx.optikeysx.core.Constants.VERIFY_EMAIL_SCREEN
import com.optiflowx.optikeysx.screens.verify_email.components.VerifyEmailContent
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
import kotlinx.coroutines.delay

class VerifyEmailScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            KeyboardSettingsModel()
        }

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000).run {
                    viewModel.reloadFirebaseUser()
                }
            }
        }

        Scaffold(
            topBar = {
                TopBar(
                    title = VERIFY_EMAIL_SCREEN,
                )
            },
            content = { padding ->
                VerifyEmailContent(
                    padding = padding,
                    reloadUser = {
                        viewModel.sendEmailVerification()
                    }
                )
            },
        )
    }
}
