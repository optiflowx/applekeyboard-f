package com.optiflowx.optikeysx.ui.keyboard

import android.content.Context
import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.optiflowx.optikeysx.MainActivity
import com.optiflowx.optikeysx.core.enums.KeyboardFontType
import com.optiflowx.optikeysx.core.enums.KeyboardType
import com.optiflowx.optikeysx.core.utils.KeyboardLocale
import com.optiflowx.optikeysx.core.utils.appFontType
import com.optiflowx.optikeysx.core.utils.boxShadow
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.viewmodels.KeyboardViewModel
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue

@Composable
fun KeyboardGlobalOptions(
    viewModel: KeyboardViewModel,
    fontType: KeyboardFontType,
    width: Dp,
    itemHeight: Int = 45,
    itemTextSize: Int = 15,
    startPadding: Int = 0,
) {
    val context = LocalContext.current
    val mIMM =
        (context.getSystemService(InputMethodService.INPUT_METHOD_SERVICE) as InputMethodManager)

    val mIMS = mIMM.currentInputMethodSubtype

    val displayName = mIMS?.getDisplayName(
        context,
        context.packageName,
        context.applicationInfo
    ).toString()

    val isShowOptions = viewModel.isShowOptions.collectAsState().value
    val options = listOf("Keyboard Settings", "Language", "Clipboard")
    val locale = viewModel.keyboardData.collectAsState().value.locale
    val keyboardLocale = KeyboardLocale(locale)

    AnimatedVisibility(isShowOptions) {
        Popup(
            alignment = Alignment.BottomStart,
            onDismissRequest = { dismiss(viewModel) },
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                clippingEnabled = false,
            )
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .width((width.value * 0.45).dp)
                    .wrapContentHeight()
                    .boxShadow(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        alpha = 0.5f,
                        offsetX = 0.dp,
                        offsetY = 0.dp,
                        blurRadius = 20.dp
                    )
                    .padding(start = startPadding.dp)
                    .clip(RoundedCornerShape(8.dp)),
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
                                .height(itemHeight.dp)
                                .clickable(onClick = { onItemClick(title, context, viewModel) })
                        ) {
                            Text(
                                text = if (title == "Language") displayName else {
                                    when (title) {
                                        "Keyboard Settings" -> keyboardLocale.keyboardSettings()
                                        "Clipboard" -> keyboardLocale.clipboard()
                                        else -> "Unknown"
                                    }
                                },
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = itemTextSize.sp.nonScaledSp,
                                    fontFamily = appFontType(fontType),
                                    color = (if (title == "Language") Color.White
                                    else MaterialTheme.colorScheme.onBackground),
                                    platformStyle = PlatformTextStyle(false)
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Stable
private fun onKeyboardSelectionType(context: Context) {
    val imId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.DEFAULT_INPUT_METHOD
    )

    val intent = Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS)
        .putExtra(Settings.EXTRA_INPUT_METHOD_ID, imId)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .putExtra(Intent.EXTRA_TITLE, "Select Your Preferred Subtype(s)")

    context.startActivity(intent)
}

@Stable
private fun navigateToKeyboardSettings(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    context.startActivity(intent)
}

@Stable
private fun onItemClick(title: String, context: Context, viewModel: KeyboardViewModel) {
    when (title) {
        "Keyboard Settings" -> navigateToKeyboardSettings(context)
        "Language" -> onKeyboardSelectionType(context)
        "Clipboard" -> switchToClipboard(viewModel)
    }
    dismiss(viewModel)
}

@Stable
private fun dismiss(viewModel: KeyboardViewModel) {
    viewModel.updateIsShowOptions(false)
}

@Stable
private fun switchToClipboard(viewModel: KeyboardViewModel) {
    viewModel.onUpdateKeyboardType(KeyboardType.Clipboard)
}