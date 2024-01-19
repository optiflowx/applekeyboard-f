package com.optiflowx.applekeyboard.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.composables.home.BooleanListTile
import com.optiflowx.applekeyboard.composables.home.StringListTile
import com.optiflowx.applekeyboard.storage.PreferencesConstants
import com.optiflowx.applekeyboard.utils.KeyboardFontType
import com.optiflowx.applekeyboard.utils.KeyboardLanguage
import com.optiflowx.applekeyboard.viewmodels.AppViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.alexzhirkevich.cupertino.CupertinoDivider
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.Surface
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ChevronBackward

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalCupertinoApi::class)
@Destination
@Composable
fun KeyboardSettingsScreen(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current
    val pC = PreferencesConstants

    val viewModel = viewModel<AppViewModel>(
        key = "AppViewModel",
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppViewModel(context) as T
            }
        }
    )

    val locale = viewModel.preferences.getFlowPreference(pC.LOCALE_KEY, "ENGLISH")
        .collectAsStateWithLifecycle("ENGLISH")
    val fontType = viewModel.preferences.getFlowPreference(pC.FONT_TYPE_KEY, "Regular")
        .collectAsStateWithLifecycle("Regular")
    val soundOn = viewModel.preferences.getFlowPreference(pC.SOUND_ON_KEY_PRESS_KEY, false)
        .collectAsStateWithLifecycle(false)
    val vibrateOn = viewModel.preferences.getFlowPreference(pC.VIBRATE_ON_KEY_PRESS_KEY, false)
        .collectAsStateWithLifecycle(false)
    val autoCapitalize = viewModel.preferences.getFlowPreference(pC.AUTO_CAPITALIZE_KEY, false)
        .collectAsStateWithLifecycle(false)
    val autoCorrect = viewModel.preferences.getFlowPreference(pC.AUTO_CORRECT_KEY, false)
        .collectAsStateWithLifecycle(false)
    val doubleSpacePeriod = viewModel.preferences.getFlowPreference(pC.DOUBLE_SPACE_PERIOD_KEY, false)
        .collectAsStateWithLifecycle(false)
    val autoCapitalizeI = viewModel.preferences.getFlowPreference(pC.AUTO_CAPITALIZE_I_KEY, false)
        .collectAsStateWithLifecycle(false)
    val showSuggestions = viewModel.preferences.getFlowPreference(pC.SHOW_SUGGESTIONS_KEY, false)
        .collectAsStateWithLifecycle(false)
    val autoCheckSpelling = viewModel.preferences.getFlowPreference(pC.AUTO_CHECK_SPELLING_KEY, false)
        .collectAsStateWithLifecycle(false)

    CupertinoScaffold(
        topBar = { KeyboardSettingsTopBar(navigator) },
        containerColor = Color.Black,
        contentColor = Color.White,
        contentWindowInsets = WindowInsets.safeContent,
    ) {
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column {
                Spacer(Modifier.height(60.dp))

                val languages = KeyboardLanguage.entries.map { it.name }
                StringListTile("Keyboard Language", locale.value, languages) {
                    viewModel.updateLocale(it)
                }

                CupertinoDivider()

                val fonts = KeyboardFontType.entries.map { it.name }
                StringListTile("Keyboard Font Type", fontType.value, fonts) {
                    viewModel.updateFontType(it)
                }

                CupertinoDivider()

                BooleanListTile(vibrateOn.value, "Vibrate On Key Press") {
                    viewModel.updateVibrateOnKeyPress(it)
                }

                CupertinoDivider()

                BooleanListTile(soundOn.value, "Sound On Key Press") {
                    viewModel.updateSoundOnKeyPress(it)
                }

                CupertinoDivider()

                BooleanListTile(doubleSpacePeriod.value, "Double Space Period") {
                    viewModel.updateDoubleSpacePeriod(it)
                }

                CupertinoDivider()

                BooleanListTile(autoCapitalize.value, "Auto-Capitalization") {
                    viewModel.updateAutoCapitalize(it)
                }

                CupertinoDivider()

                BooleanListTile(showSuggestions.value, "Show Suggestions") {
                    viewModel.updateShowSuggestions(it)
                }

                CupertinoDivider()

                BooleanListTile(autoCheckSpelling.value, "Auto Check Spelling") {
                    viewModel.updateAutoCheckSpelling(it)
                }

                CupertinoDivider()

                BooleanListTile(autoCorrect.value, "Auto-Correction") {
                    viewModel.updateAutoCorrect(it)
                }

                CupertinoDivider()

                BooleanListTile(autoCapitalizeI.value, "Auto Capitalize `I`") {
                    viewModel.updateAutoCapitalizeI(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun KeyboardSettingsTopBar(navigator: DestinationsNavigator) {
    CupertinoTopAppBar(
        isTransparent = true,
        title = { Text("Keyboard Settings", color = Color.White) },
        modifier = Modifier.padding(vertical = 5.dp),
        navigationIcon = {
            IconButton(onClick = { navigator.popBackStack() }) {
                CupertinoIcon(
                    CupertinoIcons.Default.ChevronBackward,
                    "backButton"
                )
            }
        }
    )
}