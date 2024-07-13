package com.optiflowx.optikeysx.screens.featurerequest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.viewmodels.FeatureRequestViewModel
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoButtonDefaults.filledButtonColors
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.section.CupertinoSectionDefaults
import io.github.alexzhirkevich.cupertino.section.LocalSectionStyle
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue
import io.github.alexzhirkevich.cupertino.theme.systemGray

class FeatureRequestScreen(
    private val viewModel: FeatureRequestViewModel
) : Screen {
    @OptIn(ExperimentalCupertinoApi::class)
    @Composable
    override fun Content() {
        val content = viewModel.content.collectAsState().value
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
                        CupertinoText(stringResource(R.string.feature_request))
                    },
                )
            },
            bottomBar = {
                CupertinoButton(
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxWidth(),
                    colors = filledButtonColors(
                        containerColor = if (content.isNotEmpty()) CupertinoColors.systemBlue
                        else CupertinoColors.systemGray,
                    ),
                    onClick = {
                        viewModel.submitFeatureRequest(context, navigator)
                    }
                ) {
                    CupertinoText(stringResource(R.string.request))
                }
            }
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
                CupertinoTextField(
                    value = content,
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    placeholder = {
                        CupertinoText(stringResource(R.string.feature_request_placeholder))
                    },
                    onValueChange = { data -> viewModel.setContent(data) }
                )
            }
        }
    }
}