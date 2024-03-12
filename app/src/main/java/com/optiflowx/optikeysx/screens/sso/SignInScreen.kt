package com.optiflowx.optikeysx.screens.sso

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.components.EmailField
import com.optiflowx.optikeysx.components.PasswordField
import com.optiflowx.optikeysx.components.SmallSpacer
import com.optiflowx.optikeysx.core.Constants
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
import io.github.alexzhirkevich.cupertino.CupertinoActivityIndicator
import io.github.alexzhirkevich.cupertino.CupertinoAlertDialog
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import kotlinx.coroutines.delay


class SignInScreen(val viewModel: KeyboardSettingsModel) : Screen {
    @OptIn(ExperimentalCupertinoApi::class)
    @Composable
    @ExperimentalComposeUiApi
    override fun Content() {
        val context = LocalContext.current
        val viewModel = rememberScreenModel {
            KeyboardSettingsModel()
        }

        var email by rememberSaveable(
            stateSaver = TextFieldValue.Saver,
            init = {
                mutableStateOf(
                    value = TextFieldValue(
                        text = Constants.EMPTY_STRING
                    )
                )
            }
        )

        var password by rememberSaveable(
            stateSaver = TextFieldValue.Saver,
            init = {
                mutableStateOf(
                    value = TextFieldValue(
                        text = Constants.EMPTY_STRING
                    )
                )
            }
        )

        val loader = viewModel.loader.observeAsState().value

        val keyboard = LocalSoftwareKeyboardController.current

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000).run {
                    viewModel.reloadFirebaseUser(context)
                }
            }
        }

        if (loader == true) {
            CupertinoAlertDialog(
                onDismissRequest = {  },
                title = {  },
                message = { CupertinoActivityIndicator() },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                )
            ) {
                this.action(
                    onClick = {}
                ) { CupertinoText("Loading") }
            }
        }

        CupertinoScaffold(
            topBar = {
                CupertinoTopAppBar(
//                    navigationIcon = {
//                        CupertinoIconButton(onClick = { navigator.pop() }) {
//                            CupertinoIcon(CupertinoIcons.Default.ChevronBackward, "back")
//                        }
//                    },
                    title = { CupertinoText("Sign In") }
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon_notification),
                        contentDescription = "logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(225.dp),
                    )
                    Spacer(Modifier.padding(15.dp))
                    EmailField(
                        email = email,
                        onEmailValueChange = { newValue ->
                            email = newValue
                        }
                    )
                    SmallSpacer()
                    PasswordField(
                        password = password,
                        onPasswordValueChange = { newValue ->
                            password = newValue
                        }
                    )
                }
            },
            bottomBar = {
                Surface(
                    modifier = Modifier.navigationBarsPadding(),
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CupertinoColors.systemBlue)
                            .clickable {
                                keyboard?.hide()
                                viewModel.firebaseSignInWithEmailAndPassword(
                                    email.text,
                                    password.text,
                                    context
                                )
                            }
                    ) {
                        CupertinoText(
                            text = Constants.SIGN_IN_BUTTON, modifier = Modifier.padding(15.dp)
                        )
                    }
                }
            }
        )
    }
}