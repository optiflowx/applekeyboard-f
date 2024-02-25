package com.optiflowx.optikeysx.screens

import android.inputmethodservice.InputMethodService
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodSubtype
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.regular
import com.optiflowx.optikeysx.viewmodels.KeyboardSettingsModel
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
import splitties.systemservices.inputMethodManager

class KeyboardsScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    companion object {
        const val TAG = "KeyboardsScreen"
    }

    @OptIn(ExperimentalCupertinoApi::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val settingsModel = rememberScreenModel { KeyboardSettingsModel() }

        val tileTextStyle = TextStyle(
            fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
            fontFamily = regular,
        )

        val mIMM = context.getSystemService(InputMethodService.INPUT_METHOD_SERVICE) as InputMethodManager

        val inputML: List<InputMethodInfo> = mIMM.enabledInputMethodList

        val subtypeList: List<InputMethodSubtype> = mIMM.getEnabledInputMethodSubtypeList(
            inputML[0], true
        )

        val currentTag by remember(mIMM, mIMM.currentInputMethodSubtype, subtypeList) {
            mutableStateOf(mIMM.currentInputMethodSubtype?.languageTag)
        }


        CupertinoScaffold(
            containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
            topBar = {
                CupertinoTopAppBar(
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.pop() }) {
                            CupertinoText("Home")
                        }
                    },
                    title = { CupertinoText("Keyboards") }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .statusBarsPadding()
                    .absolutePadding(top = 40.dp),
                userScrollEnabled = true
            ) {
                item("Keyboards Settings") {
                    CupertinoSection {
                        this.link(
                            title = { CupertinoText("Add Keyboard", style = tileTextStyle) },
                            onClick = { settingsModel.onAddKeyboard(context) }
                        )
                        this.link(
                            title = { CupertinoText("Change Keyboard", style = tileTextStyle) },
                            onClick = { inputMethodManager.showInputMethodPicker() }
                        )
                    }
                }
                item("Keyboards List") {
                    CupertinoSection {
                        subtypeList.forEachIndexed { index, subtype ->
                            val subtypeTag = subtype.languageTag
                            val dName = subtype.getDisplayName(
                                context,
                                context.packageName,
                                context.applicationInfo
                            )

                            val name = dName.ifEmpty { "System Default" }

                            this.link(
                                key = index,
                                title = { CupertinoText("$name", style = tileTextStyle) },
                                trailingIcon = {
                                    if (subtypeTag == currentTag) {
                                        CupertinoIcon(
                                            imageVector = CupertinoIcons.Default.CheckmarkCircle,
                                            contentDescription = "check",
                                            tint = CupertinoColors.systemBlue,
                                        )
                                    }
                                },
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}