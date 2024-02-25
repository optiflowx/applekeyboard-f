package com.optiflowx.optikeysx.screens.permissions

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.optiflowx.optikeysx.R
import com.optiflowx.optikeysx.core.utils.nonScaledSp
import com.optiflowx.optikeysx.ui.bold
import com.optiflowx.optikeysx.ui.cupertino.CupertinoDiv
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.Surface
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
            Surface(
                modifier = Modifier.navigationBarsPadding(),
            ) {
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
                            0 -> "Grant Microphone Permission"
                            1 -> "Grant Notification Access"
                            2 -> "Enable Keyboard"
                            else -> "Continue"
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
                text = "Allow OptiKeysX To Access Your Microphone And Input Method Services.",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 26.sp.nonScaledSp,
                    fontFamily = bold,
                )
            )
            Spacer(Modifier.height(5.dp))
            CupertinoDiv()
            Spacer(Modifier.height(5.dp))
            LazyColumn {


                item {
                    PermissionItem(
                        permission = "Microphone",
                        isGranted = micGranted.value,
                        imageVector = CupertinoIcons.Outlined.Mic,
                        explanation = "This permission is necessary for voice input functionality.",
                        howItWorks = "Enabling microphone access allows OptiKeysX to provide voice typing capabilities."
                    )
                }

                item {
                    PermissionItem(
                        permission = "Notifications",
                        isGranted = notificationsGranted.value,
                        imageVector = CupertinoIcons.Outlined.Bell,
                        explanation = "This permission is needed to send you important updates and notifications.",
                        howItWorks = "OptiKeysX will use notifications for downloads, updates, and important announcements."
                    )
                }

                item {
                    PermissionItem(
                        permission = "Input Method Services",
                        isGranted = imeGranted.value,
                        imageVector = CupertinoIcons.Outlined.Keyboard,
                        explanation = "This permission is required for the keyboard to function properly.",
                        howItWorks = "OptiKeysX will use IME Services to offer alternative keyboard layouts and input methods."
                    )
                }

            }
        }
    }
}

