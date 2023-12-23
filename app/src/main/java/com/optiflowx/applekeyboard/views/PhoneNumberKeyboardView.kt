package com.optiflowx.applekeyboard.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.optiflowx.applekeyboard.adapters.Key
import com.optiflowx.applekeyboard.composables.PhoneNumKeyboardKey
import com.optiflowx.applekeyboard.models.KeyboardViewModel

@Preview
@Composable
fun PhoneNumberKeyboardView() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val colors = MaterialTheme.colors

    val viewModel = viewModel<KeyboardViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return KeyboardViewModel(screenWidth.value.toInt(), colors) as T
            }
        }
    )

    val isPhoneSymbols = viewModel.isPhoneSymbol.value!!
//    val screenWidth = 480.dp
    val row1Keys = listOf(
        Key("1", ""),
        Key("2", "ABC"),
        Key("3", "DEF"),
    )

    val row2Keys = listOf(
        Key("4", "GHI"),
        Key("5", "JKL"),
        Key("6", "MNO"),
    )

    val row2KeysB = listOf(
        Key("4", "wait"),
        Key("5", "JKL"),
        Key("6", "pause"),
    )

    val row3Keys = listOf(
        Key("7", "PQRS"),
        Key("8", "TUV"),
        Key("9", "WXYZ"),
    )

    val row3KeysB = listOf(
        Key("*", "*"),
        Key("8", "TUV"),
        Key("#", "#"),
    )

    val row4Keys = listOf(
        Key("special", ""),
        Key(if (isPhoneSymbols) "+" else "0", if (isPhoneSymbols) "+" else ""),
        Key("erase", ""),
    )

    val constraints = ConstraintSet {
        val firstRow = createRefFor('1')
        val secondRow = createRefFor('2')
        val thirdRow = createRefFor('3')
        val fourthRow = createRefFor('4')
        val div1 = createRefFor("div1")
        val div2 = createRefFor("div2")
        val div3 = createRefFor("div3")


        constrain(firstRow) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
            width = Dimension.percent(100f)
        }

        constrain(div1) {
            top.linkTo(firstRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(5.dp)
            width = Dimension.percent(100f)
        }

        constrain(secondRow) {
            top.linkTo(div1.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
            width = Dimension.percent(100f)
        }

        constrain(div2) {
            top.linkTo(secondRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(5.dp)
            width = Dimension.percent(100f)
        }

        constrain(thirdRow) {
            top.linkTo(div2.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
            width = Dimension.percent(100f)
        }

        constrain(div3) {
            top.linkTo(thirdRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(5.dp)
            width = Dimension.percent(100f)
        }

        constrain(fourthRow) {
            top.linkTo(div3.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
            width = Dimension.percent(100f)
        }

        createVerticalChain(
            firstRow,
            div1,
            secondRow,
            div2,
            thirdRow,
            div3,
            fourthRow,
            chainStyle = ChainStyle.Packed
        )
    }

    val firstRowConstraints = ConstraintSet {
        val one = createRefFor('1')
        val two = createRefFor('2')
        val three = createRefFor('3')

        constrain(one) {
            start.linkTo(parent.start)
            end.linkTo(two.start)
            height = Dimension.value(55.dp)
        }

        constrain(two) {
            start.linkTo(one.end)
            end.linkTo(three.start)
            height = Dimension.value(55.dp)
        }

        constrain(three) {
            start.linkTo(two.end)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
        }
    }

    val secondRowConstraints = ConstraintSet {

        val four = createRefFor((if (isPhoneSymbols) "pause" else '4'))
        val five = createRefFor('5')
        val six = createRefFor((if (isPhoneSymbols) "wait" else '6'))

        constrain(four) {
            start.linkTo(parent.start)
            end.linkTo(five.start)
            height = Dimension.value(55.dp)
        }

        constrain(five) {
            start.linkTo(four.end)
            end.linkTo(six.start)
            height = Dimension.value(55.dp)
        }

        constrain(six) {
            start.linkTo(five.end)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
        }
    }

    val thirdRowConstraints = ConstraintSet {
        val seven = createRefFor((if (isPhoneSymbols) "*" else '7'))
        val eight = createRefFor('8')
        val nine = createRefFor((if (isPhoneSymbols) "#" else '9'))

        constrain(seven) {
            start.linkTo(parent.start)
            end.linkTo(eight.start)
            height = Dimension.value(55.dp)
        }

        constrain(eight) {
            start.linkTo(seven.end)
            end.linkTo(nine.start)
            height = Dimension.value(55.dp)
        }

        constrain(nine) {
            start.linkTo(eight.end)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
        }
    }

    val fourthRowConstraints = ConstraintSet {
        val special = createRefFor("special")
        val zero = createRefFor(if (isPhoneSymbols) "+" else "0")
        val erase = createRefFor("erase")

        constrain(special) {
            start.linkTo(parent.start)
            end.linkTo(zero.start)
            height = Dimension.value(55.dp)
        }

        constrain(zero) {
            start.linkTo(special.end)
            end.linkTo(erase.start)
            height = Dimension.value(55.dp)
        }

        constrain(erase) {
            start.linkTo(zero.end)
            end.linkTo(parent.end)
            height = Dimension.value(55.dp)
        }
    }

    ConstraintLayout(constraints, Modifier.width(screenWidth), 100) {
        val keyWidth = (screenWidth.value * 0.31).dp

        Box(Modifier.layoutId('1')) {
            ConstraintLayout(
                firstRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp), 100
            ) { for (key in row1Keys) PhoneNumKeyboardKey(key, keyWidth) }
        }
        Box(Modifier.layoutId('2')) {
            ConstraintLayout(
                secondRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp), 100
            ) {
                for (key in (if (isPhoneSymbols) row2KeysB else row2Keys)) {
                    PhoneNumKeyboardKey(key, keyWidth)
                }
            }
        }
        Box(Modifier.layoutId('3')) {
            ConstraintLayout(
                thirdRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .padding(horizontal = 2.dp),
                100
            ) {
                for (key in (if (isPhoneSymbols) row3KeysB else row3Keys)) {
                    PhoneNumKeyboardKey(key, keyWidth)
                }
            }
        }
        Box(Modifier.layoutId('4')) {
            ConstraintLayout(
                fourthRowConstraints,
                Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 2.dp), 100
            ) { for (key in row4Keys) PhoneNumKeyboardKey(key, keyWidth) }
        }
    }
}