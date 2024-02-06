@file:Suppress("UNCHECKED_CAST")

package com.optiflowx.optikeysx.screens

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.inputmethod.InputMethodInfo
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
import com.optiflowx.optikeysx.core.preferences.PrefsConstants
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.regular
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
import splitties.systemservices.inputMethodManager

private fun onAddKeyboard(context: Context) {
    val imId = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.DEFAULT_INPUT_METHOD
    )

    val intent = Intent(Settings.ACTION_INPUT_METHOD_SUBTYPE_SETTINGS)
        .putExtra(Settings.EXTRA_INPUT_METHOD_ID, imId)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .putExtra(Intent.EXTRA_TITLE, "Select Enabled Subtypes")
    context.startActivity(intent);
}

@OptIn(ExperimentalCupertinoApi::class)
@Composable
@Destination
fun KeyboardsScreen(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val pC = PrefsConstants

    val tileTextStyle = TextStyle(
        fontSize = TextUnit(17f, TextUnitType.Sp).nonScaledSp,
        fontFamily = regular,
    )

    val mIMM = inputMethodManager

    val inputML: List<InputMethodInfo> = mIMM.enabledInputMethodList

    val subtypeList: List<InputMethodSubtype> = mIMM.getEnabledInputMethodSubtypeList(
        inputML[0], true
    )

    val mIMS = mIMM.showInputMethodPicker()

    val id = inputML[0].id

//    val token = mIMS.window.window?.attributes?.token

    val currentTag by remember(mIMM, mIMM.currentInputMethodSubtype, subtypeList) {
        mutableStateOf(mIMM.currentInputMethodSubtype?.languageTag)
    }

    CupertinoScaffold(
        containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
        topBar = {
            CupertinoTopAppBar(
                navigationIcon = {
                    CupertinoNavigateBackButton(onClick = { navigator.popBackStack() }) {
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
                        onClick = { onAddKeyboard(context) }
                    )
                    this.link(
                        title = { CupertinoText("Change Keyboard", style = tileTextStyle) },
                        onClick = { mIMM.showInputMethodPicker() }
                    )
                }
            }
            item("Keyboards List") {
                CupertinoSection {
                    subtypeList.forEachIndexed { index, subtype ->
                        val subtypeTag = subtype.languageTag
                        val name = subtype.getDisplayName(
                            context,
                            context.packageName,
                            context.applicationInfo
                        )

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
                            onClick = {
//                                if(token != null) {
//                                    @Suppress("DEPRECATION")
//                                    mIMM.setInputMethodAndSubtype(token,id, subtype)
//                                }
                            }
                        )
                    }
                }
            }
        }
    }
}