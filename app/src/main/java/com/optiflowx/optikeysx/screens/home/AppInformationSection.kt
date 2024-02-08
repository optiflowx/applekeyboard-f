package com.optiflowx.optikeysx.screens.home

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.optiflowx.optikeysx.ui.cupertino.CupertinoTile
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun AppInformationSection(
    titleTextStyle: TextStyle
) {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

    CupertinoSection(
        title = { CupertinoText("APP INFORMATION", style = titleTextStyle) }
    ) {
        this.item(0) {
            CupertinoTile(
                title = "Version",
                trailingText = packageInfo.versionName,
                trailingIcon = null,
            )
        }
        this.item(1) {
            CupertinoTile(
                title = "Package Name",
                trailingText = packageInfo.packageName,
                trailingIcon = null,
            )
        }
        this.item(2) {
            CupertinoTile(
                title = "Build Version",
                trailingText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode.toString()
                } else {
                    @Suppress("DEPRECATION")
                    packageInfo.versionCode.toString()
                },
                trailingIcon = null,
            )
        }
    }
}