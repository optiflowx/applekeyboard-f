package com.optiflowx.optikeysx.app.screens.featurerequest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.app.viewmodels.FeatureRequestViewModel
import com.optiflowx.optikeysx.ime.views.keyboards.standard.Gap
import io.github.alexzhirkevich.cupertino.CupertinoActivityIndicator
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.PlusCircle
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGray
import io.github.alexzhirkevich.cupertino.theme.systemGray4
import io.github.alexzhirkevich.cupertino.theme.systemGreen
import io.github.alexzhirkevich.cupertino.theme.systemRed

class FeatureRequestsScreen : Screen {
    @OptIn(ExperimentalCupertinoApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { FeatureRequestViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val requests = viewModel.requests.asLiveData().value ?: emptyList()
        val isLoading = viewModel.isLoading.collectAsState().value


        if (isLoading) {
            BasicAlertDialog(
                onDismissRequest = {}
            ) {
                CupertinoActivityIndicator()
            }
        }

        CupertinoScaffold(
            containerColor = CupertinoSectionDefaults.containerColor(LocalSectionStyle.current),
            topBar = {
                CupertinoTopAppBar(
                    title = {
                        CupertinoText(stringResource(R.string.feature_requests))
                    },
                    navigationIcon = {
                        CupertinoNavigateBackButton(onClick = { navigator.pop() }) {
                            CupertinoText(stringResource(R.string.back))
                        }
                    },
                    actions = {
                        CupertinoIconButton(
                            onClick = {
                                navigator.push(FeatureRequestScreen(viewModel))
                            }
                        ) {
                            CupertinoIcon(
                                imageVector = CupertinoIcons.Default.PlusCircle,
                                contentDescription = "add feature request"
                            )
                        }
                    },
                )
            },
        ) {
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(
                        top = 55.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
            ) {
                LazyColumn {
                    items(
                        count = requests.size,
                        key = { requests[it].time }
                    ) { index ->
                        val request = requests[index]

                        Column {
                            Gap()
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = CupertinoColors.systemGray4,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        CupertinoText(
                                            text = request.time,
                                            style = TextStyle(fontSize = 14.sp),
                                        )
                                        Gap()
                                        CupertinoText(
                                            text = request.content,
                                            style = TextStyle(fontSize = 18.sp),
                                        )
                                    }


                                    CupertinoText(
                                        text = request.status,
                                        style = TextStyle(fontSize = 14.sp),
                                        modifier = Modifier
                                            .background(
                                                shape = RoundedCornerShape(15.dp),
                                                color = when (request.status) {
                                                    "Open" -> CupertinoColors.systemGray
                                                    "In Progress" -> CupertinoColors.systemBlue
                                                    "Done" -> CupertinoColors.systemGreen
                                                    "Declined" -> CupertinoColors.systemRed
                                                    else -> CupertinoColors.systemGray
                                                }
                                            )
                                            .padding(
                                                vertical = 2.dp,
                                                horizontal = 5.dp
                                            )
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