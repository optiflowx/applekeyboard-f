@file:Suppress("UNCHECKED_CAST")

package com.optiflowx.optikeysx.screens

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.optikeysx.core.enums.KeyboardLanguage
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.preferences.rememberPreference
import com.optiflowx.optikeysx.ui.regular
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.viewmodels.AppViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.CheckmarkCircle
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle
import io.github.alexzhirkevich.cupertino.section.link
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue

@OptIn(ExperimentalCupertinoApi::class)
@Composable
@Destination
fun KeyboardsScreen(navigator: DestinationsNavigator) {
    val keyboards = KeyboardLanguage.entries
    val context = LocalContext.current
    val pC = PrefsConstants

    val tileTextStyle = TextStyle(
        fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
        fontFamily = regular,
    )

    val viewModel = viewModel<AppViewModel>(
        key = "AppViewModel",
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AppViewModel(context) as T
            }
        }
    )

    val locale by rememberPreference(pC.LOCALE_KEY, "English")

    CupertinoScaffold(
        containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
        topBar = {
            CupertinoTopAppBar(
                navigationIcon = {
                    CupertinoNavigateBackButton(onClick = {  navigator.popBackStack()}) {
                        CupertinoText("Home")
                    }
                },
                title = { CupertinoText("Keyboards") }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.statusBarsPadding().absolutePadding(top = 40.dp),
            userScrollEnabled = true
        ) {
            item("Keyboards List") {
                CupertinoSection {
                    keyboards.forEachIndexed { index, language ->
                        this.link(
                            key = index,
                            title = { CupertinoText(language.name, style = tileTextStyle) },
                            trailingIcon = {
                                if(language.name == locale) {
                                    CupertinoIcon(
                                        imageVector = CupertinoIcons.Default.CheckmarkCircle,
                                        contentDescription = "check",
                                        tint = CupertinoColors.systemBlue,
                                    )
                                }
                            },
                            onClick = { viewModel.updateLocale(language.name) }
                        )
                    }
                }
            }
        }
    }
//    navigator: DestinationsNavigator

}