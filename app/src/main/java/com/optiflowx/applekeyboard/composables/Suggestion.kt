package com.optiflowx.applekeyboard.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.models.KeyboardViewModel
import com.optiflowx.applekeyboard.services.IMEService
import com.optiflowx.applekeyboard.ui.defaultFontFamily

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun Suggestion(id: String, width: Dp = 130.dp) {
    val constraints = ConstraintSet {
        val text = createRefFor("text")
        constrain(text) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val colors = MaterialTheme.colors
    val imeService = LocalContext.current as IMEService
    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(screenWidth, colors) as T
            }
        }
    )

//    val inputText = imeService.currentInputEditorInfo.getInitialSelectedText(InputConnection.GET_TEXT_WITH_STYLES)

//    val inputText by remember {
//        mutableStateOf(imeService.currentInputConnection.getTextBeforeCursor(3, 0))
//    }
//
//
//
//    LaunchedEffect(imeService.currentInputConnection.getTextBeforeCursor(55, 0)) {
//        viewModel.wordsList.value = viewModel.wordsList.value?.filter {
//            it.startsWith(imeService.currentInputConnection.getTextBeforeCursor(3, 0).toString())
//        }
//
//        if(inputText != null) {
//            Log.d("Suggestion", inputText.toString())
//        }
//    }
//
//    val suggestedWords = viewModel.wordsList.observeAsState().value?.sorted()?.take(3)
//    val suggestedWords = viewModel.wordsList.observeAsState().value?.binarySearch {
//        it.compareTo(id)
//    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .layoutId(id)
            .fillMaxHeight()
            .width(width),
    ) {

        ConstraintLayout(constraints) {
            Text(
                text = " ",
                style = TextStyle(
                    color = Color.White,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    fontFamily = defaultFontFamily,
                ),
                modifier = Modifier.layoutId("text").fillMaxHeight().width(width),
            )
        }
    }
}