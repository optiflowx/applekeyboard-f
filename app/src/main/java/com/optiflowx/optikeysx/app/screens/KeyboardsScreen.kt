package com.optiflowx.optikeysx.app.screens

import android.inputmethodservice.InputMethodService
import android.view.inputmethod.InputMethodInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodSubtype
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.theme.regular
import com.optiflowx.optikeysx.app.viewmodels.KeyboardSettingsModel
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
import io.github.alexzhirkevich.cupertino.section.SectionLink
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

        val mIMM =
            context.getSystemService(InputMethodService.INPUT_METHOD_SERVICE) as InputMethodManager

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
                            CupertinoText(stringResource(R.string.back))
                        }
                    },
                    title = { CupertinoText(stringResource(R.string.keyboards_general)) }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .absolutePadding(top = 40.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CupertinoSection {
                    SectionLink(
                        title = {
                            CupertinoText(
                                stringResource(R.string.add_keyboard),
                                style = tileTextStyle
                            )
                        },
                        onClick = { settingsModel.onAddKeyboard(context) }
                    )
                    SectionLink(
                        title = {
                            CupertinoText(
                                stringResource(R.string.change_keyboard),
                                style = tileTextStyle
                            )
                        },
                        onClick = { inputMethodManager.showInputMethodPicker() }
                    )
                }

                CupertinoSection {
                    subtypeList.forEach { subtype ->
                        val subtypeTag = subtype.languageTag
                        val dName = subtype.getDisplayName(
                            context,
                            context.packageName,
                            context.applicationInfo
                        )

                        val name = dName.ifEmpty { stringResource(R.string.system_default) }

                        SectionLink(
                            title = { CupertinoText("$name", style = tileTextStyle) },
                            onClick = {},
                            chevron = {
                                if (subtypeTag == currentTag) {
                                    CupertinoIcon(
                                        imageVector = CupertinoIcons.Default.CheckmarkCircle,
                                        contentDescription = "check",
                                        tint = CupertinoColors.systemBlue,
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}