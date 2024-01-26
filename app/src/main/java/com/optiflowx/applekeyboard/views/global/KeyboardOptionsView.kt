package com.optiflowx.applekeyboard.views.global

import android.content.Intent
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.optiflowx.applekeyboard.MainActivity
import com.optiflowx.applekeyboard.core.enums.KeyboardType
import com.optiflowx.applekeyboard.utils.appFontType
import com.optiflowx.applekeyboard.utils.boxShadow
import com.optiflowx.applekeyboard.utils.nonScaledSp
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue

@Composable
fun KeyboardOptionsView(
    viewModel: KeyboardViewModel,
    locale: String,
    fontType: String
) {
    val width = LocalConfiguration.current.screenWidthDp
    val showOptions = viewModel.isShowOptions.observeAsState(false).value
    val options = listOf("Keyboard Settings", "Language", "Clipboard")
    val context = LocalContext.current

    if (showOptions) {
        Popup(
            alignment = Alignment.BottomStart,
            onDismissRequest = { viewModel.isShowOptions.value = false },
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                securePolicy = SecureFlagPolicy.SecureOff,
                dismissOnClickOutside = true,
                clippingEnabled = true,
            )
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .width((width * 0.5).dp)
                    .wrapContentHeight()
                    .padding(start = 10.dp, bottom = 25.dp)
                    .boxShadow(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        alpha = 0.25f,
                        borderRadius = 20.dp,
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        blurRadius = 10.dp,
                        spread = 15.dp,
                    )
                    .clip(RoundedCornerShape(10.dp)),
            ) {
                Column {
                    options.forEach { title ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background((title == "Language").let {
                                    if (it) CupertinoColors.systemBlue
                                    else Color.Transparent
                                })
                                .fillMaxWidth()
                                .height(45.dp)
                                .clickable(
                                    indication = LocalIndication.current,
                                    interactionSource = remember { MutableInteractionSource() },
                                    role = Role.Button,
                                ) {
                                    when (title) {
                                        "Keyboard Settings" -> {
                                            context.startActivity(Intent(
                                                context,
                                                MainActivity::class.java
                                            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                                        }

                                        "Language" -> {
                                            //TODO: Add language selection
                                        }

                                        "Clipboard" -> {
                                            viewModel.keyboardType.value = KeyboardType.Clipboard
                                        }
                                    }

                                    viewModel.isShowOptions.value = false
                                }
                        ) {
                            Text(
                                text = if (title == "Language") locale else title,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 16.sp.nonScaledSp,
                                    fontFamily = appFontType(fontType),
                                    color = (if (title == "Language") Color.White
                                    else MaterialTheme.colorScheme.onBackground),
                                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}