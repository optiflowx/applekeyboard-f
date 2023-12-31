@file:Suppress("UNCHECKED_CAST")

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.composables.home.BooleanListTile
import com.optiflowx.applekeyboard.composables.home.StringListTile
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

@OptIn(ExperimentalCupertinoApi::class)
@Destination
@Composable
fun KeyboardSettingsScreen(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val viewModel = viewModel<AppViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppViewModel(context) as T
            }
        }
    )

    val locale = viewModel.locale.observeAsState().value

    val autoCapitalizeFistWord = viewModel.autoCapitalizeFirstWord.observeAsState().value

    val fontType = viewModel.fontType.observeAsState().value

    val soundOnKeyPress = viewModel.soundOnKeyPress.observeAsState().value

    val vibrateOnKeyPress = viewModel.vibrateOnKeyPress.observeAsState().value

    val showSuggestions = viewModel.showSuggestions.observeAsState().value

    val showEmojiSearchBar = viewModel.showEmojiSearchBar.observeAsState().value

    val autoCapitalizeI = viewModel.autoCapitalizeI.observeAsState().value

    val autoCapitalize = viewModel.autoCapitalize.observeAsState().value

    val autoCorrect = viewModel.autoCorrect.observeAsState().value

    val autoCheckSpelling = viewModel.autoCheckSpelling.observeAsState().value

    val doubleSpacePeriod = viewModel.doubleSpacePeriod.observeAsState().value

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

                val languages = listOf("en", "es", "pt", "fr")
                StringListTile("Keyboard Language", locale?:"", languages) {
                    viewModel.updateStringColumn("locale", it)
                }

                CupertinoDivider()

                val fonts = listOf("regular", "medium", "bold")
                StringListTile("Keyboard Font Type", fontType?:"", fonts) {
                    viewModel.updateStringColumn("fontType", it)
                }

                CupertinoDivider()

                BooleanListTile(vibrateOnKeyPress ?: false, "Vibrate On Key Press") {
                    viewModel.updateBooleanColumn("vibrateOnKeyPress", it)
                }

                CupertinoDivider()

                BooleanListTile(soundOnKeyPress ?: false, "Sound On Key Press") {
                    viewModel.updateBooleanColumn("soundOnKeyPress", it)
                }

                CupertinoDivider()

                BooleanListTile(showEmojiSearchBar ?: false, "Show Emoji Search Bar") {
                    viewModel.updateBooleanColumn("showEmojiSearchBar", it)
                }

                CupertinoDivider()

                BooleanListTile(doubleSpacePeriod ?: false, "Double Space Period") {
                    viewModel.updateBooleanColumn("doubleSpacePeriod", it)
                }

                CupertinoDivider()

                BooleanListTile(autoCapitalize ?: false, "Auto-Capitalization") {
                    viewModel.updateBooleanColumn("autoCapitalize", it)
                }

                CupertinoDivider()

                BooleanListTile(showSuggestions ?: false, "Show Suggestions") {
                    viewModel.updateBooleanColumn("showSuggestions", it)
                }

                CupertinoDivider()

                BooleanListTile(autoCheckSpelling ?: false, "Auto Check Spelling") {
                    viewModel.updateBooleanColumn("autoCheckSpelling", it)
                }

                CupertinoDivider()

                BooleanListTile(autoCorrect ?: false, "Auto-Correction") {
                    viewModel.updateBooleanColumn("autoCorrect", it)
                }

                CupertinoDivider()

                BooleanListTile(autoCapitalizeFistWord ?: false, "Auto-Capitalize First Word") {
                    viewModel.updateBooleanColumn("autoCapitalizeFirstWord", it)
                }

                CupertinoDivider()

                BooleanListTile(autoCapitalizeI ?: false, "Auto Capitalize `I`") {
                    viewModel.updateBooleanColumn("autoCapitalizeI", it)
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