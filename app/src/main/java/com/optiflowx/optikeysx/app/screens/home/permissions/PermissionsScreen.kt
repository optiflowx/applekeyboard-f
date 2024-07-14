package com.optiflowx.optikeysx.app.screens.home.permissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.app.cupertino.CupertinoDiv
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ime.theme.bold
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoSurface
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Bell
import io.github.alexzhirkevich.cupertino.icons.outlined.Keyboard
import io.github.alexzhirkevich.cupertino.icons.outlined.Mic
import io.github.alexzhirkevich.cupertino.theme.CupertinoColors
import io.github.alexzhirkevich.cupertino.theme.systemBlue

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun PermissionsScreen(
    micGranted: State<Boolean>,
    imeGranted: State<Boolean>,
    notificationsGranted: State<Boolean>,
    state: State<Int>,
    onClick: () -> Unit
) {

    CupertinoScaffold(
        modifier = Modifier.fillMaxWidth(),
        bottomBar = {
            CupertinoSurface(modifier = Modifier.navigationBarsPadding()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(CupertinoColors.systemBlue)
                        .clickable { onClick() }
                ) {
                    CupertinoText(
                        when (state.value) {
                            0 -> stringResource(R.string.grant_microphone_permission)
                            1 -> stringResource(R.string.grant_notification_access)
                            2 -> stringResource(R.string.enable_keyboard)
                            else -> stringResource(R.string.continue_string)
                        }, modifier = Modifier.padding(15.dp)
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(
                    vertical = 30.dp,
                    horizontal = 15.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.icon_notification),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(125.dp),
            )
            Spacer(Modifier.padding(15.dp))
            CupertinoText(
                text = stringResource(R.string.microphone_permission_message),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 26.sp.nonScaledSp,
                    fontFamily = bold,
                )
            )
            Spacer(Modifier.height(5.dp))
            CupertinoDiv()
            Spacer(Modifier.height(5.dp))
            Column {
                PermissionItem(
                    permission = stringResource(R.string.microphone_access),
                    isGranted = micGranted.value,
                    imageVector = CupertinoIcons.Outlined.Mic,
                    explanation = stringResource(R.string.microphone_access_description),
                    howItWorks = stringResource(R.string.enable_microphone_access)
                )

                PermissionItem(
                    permission = stringResource(R.string.notification_access),
                    isGranted = notificationsGranted.value,
                    imageVector = CupertinoIcons.Outlined.Bell,
                    explanation = stringResource(R.string.notification_access_description),
                    howItWorks = stringResource(R.string.enable_notification_access)
                )

                PermissionItem(
                    permission = stringResource(R.string.input_method_services),
                    isGranted = imeGranted.value,
                    imageVector = CupertinoIcons.Outlined.Keyboard,
                    explanation = stringResource(R.string.input_method_services_description),
                    howItWorks = stringResource(R.string.enable_ime_services)
                )
            }
        }
    }
}

