package com.optiflowx.optikeysx.ui.cupertino

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.core.data.ModelLink
import com.optiflowx.optikeysx.core.downloader.FileDownloader
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.ArrowDownCircle
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue

@OptIn(ExperimentalCupertinoApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DownloadModelBottomSheet(
    onSheetChange: (Boolean) -> Unit,
    sheetSectionColor: Color,
    modelOrder: List<InstalledModelReference>,
) {

    val context = LocalContext.current

    ModalBottomSheet(
        containerColor = sheetSectionColor,
        onDismissRequest = { onSheetChange(false) }) {
        CupertinoSection(containerColor = Color.Transparent) {
            item {
                LazyColumn {
                    ModelLink.entries.filter { ml ->
                        modelOrder.none { model ->
                            ml.link.substring(
                                ml.link.lastIndexOf('/'),
                                ml.link.lastIndexOf('.')
                            ) == model.path.substring(
                                model.path.lastIndexOf('/')
                            )
                        }
                    }.forEach { ml ->
                        this.item(ml.name) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth().padding(it).clickable(
                                    onClick = {
                                        onSheetChange(false)
                                        FileDownloader.downloadModel(ml, context)
                                    }
                                )
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