package com.optiflowx.optikeysx.screens.home.components

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.R
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.SectionItem

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AppInformationSection(
    titleTextStyle: TextStyle
) {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

    CupertinoSection(
        title = { CupertinoText(stringResource(R.string.app_information), style = titleTextStyle) }
    ) {
        SectionItem(
            title = { CupertinoText(stringResource(R.string.version)) },
            trailingContent = { CupertinoText(packageInfo.versionName) }
        )
        SectionItem(
            title = { CupertinoText(stringResource(R.string.package_name)) },
            trailingContent = { CupertinoText(packageInfo.packageName) }
        )
        SectionItem(
            title = { CupertinoText(stringResource(R.string.build_version)) },
            trailingContent = {
                CupertinoText(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        packageInfo.longVersionCode.toString()
                    } else {
                        @Suppress("DEPRECATION")
                        packageInfo.versionCode.toString()
                    }
                )
            }
        )
    }
}