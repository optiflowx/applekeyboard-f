package com.optiflowx.optikeysx.screens.sso

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.components.EmailField
import com.optiflowx.optikeysx.components.NameField
import com.optiflowx.optikeysx.components.PasswordField
import com.optiflowx.optikeysx.components.SmallSpacer
import com.optiflowx.optikeysx.core.Constants
import com.optiflowx.optikeysx.core.model.AppUserData
import com.optiflowx.optikeysx.core.utils.nonScaledSp
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

class SignUpScreen(val viewModel: KeyboardSettingsModel) : Screen {
    @OptIn(ExperimentalCupertinoApi::class)
    @ExperimentalComposeUiApi
    @Composable
    override fun Content() {
        val keyboard = LocalSoftwareKeyboardController.current
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        var name by rememberSaveable(
            stateSaver = TextFieldValue.Saver,
            init = {
                mutableStateOf(
                    value = TextFieldValue(
                        text = Constants.EMPTY_STRING
                    )
                )
            }
        )

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
                    title = { CupertinoText(Constants.SIGN_UP_BUTTON) }
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
                    NameField(
                        name = name,
                        onNameValueChange = { newName ->
                            name = newName
                        }
                    )
                    SmallSpacer()
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
                    Row {
                        Text("Already a user? ")
                        Text(
                            "Sign In", modifier = Modifier.clickable {
                                navigator.push(SignInScreen(viewModel))
                            }, style = TextStyle(
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp.nonScaledSp
                            )
                        )
                    }

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
                                val user = AppUserData(
                                    name.text, email.text, password.text
                                )

                                keyboard?.hide()

                                viewModel.firebaseSignUpWithEmailAndPassword(context, user)
                            }
                    ) {
                        CupertinoText(
                            text = Constants.SIGN_UP_BUTTON, modifier = Modifier.padding(15.dp)
                        )
                    }
                }
            }
        )
    }
}