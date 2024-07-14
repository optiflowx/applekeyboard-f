package com.optiflowx.optikeysx.app.screens.speech

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.core.data.ModelLink
import com.optiflowx.optikeysx.core.downloader.FileDownloader
import com.optiflowx.optikeysx.ime.theme.bold
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ArrowDownCircle
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle
import io.github.alexzhirkevich.cupertino.section.SectionItem
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.systemBlue

class ModelDownloadScreen(private val modelsList: List<InstalledModelReference>) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalCupertinoApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        CupertinoScaffold(
            containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
            topBar = {
                CupertinoTopAppBar(
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.pop() }) {
                            CupertinoText(stringResource(R.string.back))
                        }
                    },
                    title = {
                        CupertinoText(
                            text = stringResource(R.string.model_download),
                            fontFamily = bold,
                        )
                    }
                )
            }
        ) {
            CupertinoSection(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 60.dp),
                color = CupertinoTheme.colorScheme.tertiarySystemBackground,
            ) {
                SectionItem {
                    LazyColumn {
                        ModelLink.entries.filter { ml ->
                            modelsList.none { model ->
                                ml.link.substring(
                                    ml.link.lastIndexOf('/'), ml.link.lastIndexOf('.')
                                ) == model.path.substring(
                                    model.path.lastIndexOf('/')
                                )
                            }
                        }.forEach { ml ->
                            this.item(ml.name) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick = {
                                                FileDownloader.downloadModel(ml, context)
                                            },
                                        ),
                                ) {
                                    CupertinoText(
                                        text = ml.locale.displayName,
                                        modifier = Modifier.padding(10.dp),
                                    )

                                    Icon(
                                        imageVector = CupertinoIcons.Default.ArrowDownCircle,
                                        modifier = Modifier.height(25.dp),
                                        contentDescription = "download",
                                        tint = CupertinoColors.systemBlue
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}