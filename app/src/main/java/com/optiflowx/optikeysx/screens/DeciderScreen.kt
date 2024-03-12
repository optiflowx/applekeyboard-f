package com.optiflowx.optikeysx.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.optiflowx.optikeysx.screens.home.HomeScreen
import com.optiflowx.optikeysx.screens.sso.SignInScreen
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
import dev.patrickgold.jetpref.datastore.model.observeAsState

class DeciderScreen : Screen {
    override val key = "DeciderScreen"
    @Composable
    override fun Content() {
        val vm = rememberScreenModel { KeyboardSettingsModel() }

        val isAuth = vm.prefs.isAuthenticated.observeAsState().value

        if (isAuth) {
//                        if (user.isEmailVerified) {
            Navigator(HomeScreen(vm))
//                        } else Navigator(VerifyEmailScreen())
        } else {
            Navigator(SignInScreen(vm))
        }
    }
}