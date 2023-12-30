package com.optiflowx.applekeyboard.composables.keyboard

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.os.LocaleListCompat
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.ui.cupertinoBlue1
import com.optiflowx.applekeyboard.ui.defaultFontFamily
import io.github.alexzhirkevich.cupertino.Surface

@Composable
fun LanguageSelectionPopup(height: Float, width: Float, viewModel: KeyboardViewModel) {
    val x: Int = (width * 0.25).toInt()
    val y: Int = (height * 0.25).toInt()

    Popup(
        alignment = Alignment.CenterStart,
        onDismissRequest = { viewModel.showPopup.value = false },
        offset = IntOffset(x, y),
        properties = PopupProperties(
            dismissOnBackPress = false,
            focusable = false,
            dismissOnClickOutside = true,
            excludeFromSystemGesture = true,
            clippingEnabled = true
        )
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(12.dp),
                    clip = false,
                    spotColor = MaterialTheme.colorScheme.inversePrimary
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Column(Modifier.padding(5.dp)) {
                SelectionText("English(EN)", viewModel)
                SelectionText("Spanish(ES)", viewModel)
                SelectionText("French(FR)", viewModel)
                SelectionText("Portuguese(PT)", viewModel)
            }
        }
    }
}

@Composable
fun SelectionText(t: String, viewModel: KeyboardViewModel) {
    val selectedLanguage = viewModel.currentLanguage.observeAsState().value!!
    val splitText = t.split("(")
    val language = splitText[1].subSequence(0, 2).toString().lowercase()
    Surface(
        onClick = { viewModel.changeLocale(language) },
        shape = RoundedCornerShape(6.dp),
        color = (if (language == selectedLanguage) cupertinoBlue1 else MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = t,
            modifier = Modifier.padding(horizontal = 5.dp),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = defaultFontFamily,
            fontSize = TextUnit(16f, TextUnitType.Sp),
        )
    }
}