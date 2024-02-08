package com.optiflowx.optikeysx.ui.cupertino

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.core.data.InstalledModelReference
import com.optiflowx.optikeysx.core.data.ModelLink
import com.optiflowx.optikeysx.core.downloader.FileDownloader
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalCupertinoApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DownloadModelBottomSheet(
    scaffoldState: CupertinoBottomSheetScaffoldState,
    sheetSectionColor: Color,
    modelOrder: List<InstalledModelReference>,
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    fun closeSheet() {
        coroutineScope.launch {
            scaffoldState.bottomSheetState.hide()
        }
    }

    CupertinoBottomSheetContent(
        topBar = {
            CupertinoTopAppBar(
                title = {
                    CupertinoText("Model Downloads")
                },
                actions = {
                    CupertinoButton(
                        colors = CupertinoButtonDefaults.borderlessButtonColors(),
                        onClick = { closeSheet() }
                    ) {
                        CupertinoText("Cancel")
                    }
                },
                isTransparent = true
            )
        }
    ) { pv ->
        CupertinoSection(
            modifier = Modifier.padding(pv),
            containerColor = Color.Transparent,
            color = sheetSectionColor
        ) {
            item { it ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth()
                        .heightIn(0.dp, (LocalConfiguration.current.screenHeightDp * 0.9).dp)
                ) {
                    item {
                        ModelLink.entries.filter { ml ->
                            modelOrder.none {
                                ml.link.substring(
                                    ml.link.lastIndexOf('/'),
                                    ml.link.lastIndexOf('.')
                                ) == it.path.substring(
                                    it.path.lastIndexOf('/')
                                )
                            }
                        }.forEach { ml ->
                            Card(
                                onClick = {
                                    closeSheet()
                                    FileDownloader.downloadModel(ml, context)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.onSurface.copy(0.2f))
                                        .padding(10.dp)
                                ) {
                                    Text(text = ml.locale.displayName)
                                    Text(text = ml.link, fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}