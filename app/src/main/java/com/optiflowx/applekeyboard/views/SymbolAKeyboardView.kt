package com.optiflowx.applekeyboard.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.optiflowx.applekeyboard.R
import com.optiflowx.applekeyboard.composables.keyboard.KeyboardKey
import com.optiflowx.applekeyboard.models.Key
import com.optiflowx.applekeyboard.viewmodels.KeyboardViewModel


@Composable
fun SymbolAKeyboardView(viewModel: KeyboardViewModel) {
    val isSymbol = viewModel.isNumberSymbol.observeAsState().value
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val row1Keys = arrayOf(
        Key("1", "1"),
        Key("2", "2"),
        Key("3", "3"),
        Key("4", "4"),
        Key("5", "5"),
        Key("6", "6"),
        Key("7", "7"),
        Key("8", "8"),
        Key("9", "9"),
        Key("0", "0")
    )

    val row1SymbolKeys = arrayOf(
        Key("1", "["),
        Key("2", "]"),
        Key("3", "{"),
        Key("4", "}"),
        Key("5", "#"),
        Key("6", "%"),
        Key("7", "^"),
        Key("8", "*"),
        Key("9", "+"),
        Key("0", "=")
    )

    val row2Keys = arrayOf(
        Key("-", "-"),
        Key("/", "/"),
        Key(":", ":"),
        Key(";", ";"),
        Key("(", "("),
        Key(")", ")"),
        Key("$", "$"),
        Key("&", "&"),
        Key("@", "@"),
        Key("\"", "\"")
    )

    val row2SymbolKeys = arrayOf(
        Key("-", "_"),
        Key("/", "\\"),
        Key(":", "|"),
        Key(";", "~"),
        Key("(", "<"),
        Key(")", ">"),
        Key("$", "€"),
        Key("&", "£"),
        Key("@", "˚"),
        Key("\"", "•")
    )

    val row3Keys = arrayOf(
        Key(".", "."),
        Key(",", ","),
        Key("?", "?"),
        Key("!", "!"),
        Key("'", "'")
    )

    val constraints = ConstraintSet {
        val topGuideline = createGuidelineFromTop(10.dp)

        val firstRow = createRefFor('1')
        val secondRow = createRefFor('2')
        val thirdRow = createRefFor('3')
        val fourthRow = createRefFor('4')

        constrain(firstRow) {
            top.linkTo(topGuideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(56.dp)
            width = Dimension.percent(100f)
        }

        constrain(secondRow) {
            top.linkTo(firstRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(54.dp)
            width = Dimension.percent(100f)
        }

        constrain(thirdRow) {
            top.linkTo(secondRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(54.dp)
            width = Dimension.percent(100f)
        }

        constrain(fourthRow) {
            top.linkTo(thirdRow.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.value(58.dp)
            width = Dimension.percent(100f)
        }

        createVerticalChain(
            firstRow,
            secondRow,
            thirdRow,
            fourthRow,
            chainStyle = ChainStyle.Packed
        )
    }

    val firstRowConstraints = ConstraintSet {
        val p = createRefFor("1")
        val m = createRefFor("2")
        val d = createRefFor("3")
        val e = createRefFor("4")
        val s = createRefFor("5")
        val u = createRefFor("6")
        val lT = createRefFor("7")
        val gT = createRefFor("8")
        val sL = createRefFor("9")
        val sR = createRefFor("0")

        constrain(p) {
            start.linkTo(parent.start)
            end.linkTo(m.start)
            height = Dimension.value(45.dp)
        }

        constrain(m) {
            start.linkTo(p.end)
            end.linkTo(d.start)
            height = Dimension.value(45.dp)
        }

        constrain(d) {
            start.linkTo(m.end)
            end.linkTo(e.start)
            height = Dimension.value(45.dp)
        }

        constrain(e) {
            start.linkTo(d.end)
            end.linkTo(s.start)
            height = Dimension.value(45.dp)
        }

        constrain(s) {
            start.linkTo(e.end)
            end.linkTo(u.start)
            height = Dimension.value(45.dp)
        }

        constrain(u) {
            start.linkTo(s.end)
            end.linkTo(lT.start)
            height = Dimension.value(45.dp)
        }

        constrain(lT) {
            start.linkTo(u.end)
            end.linkTo(gT.start)
            height = Dimension.value(45.dp)
        }

        constrain(gT) {
            start.linkTo(lT.end)
            end.linkTo(sL.start)
            height = Dimension.value(45.dp)
        }

        constrain(sL) {
            start.linkTo(gT.end)
            end.linkTo(sR.start)
            height = Dimension.value(45.dp)
        }

        constrain(sR) {
            start.linkTo(sL.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
        }

        createHorizontalChain(p,m,d,e,s,u,lT,gT,sL,sR, chainStyle = ChainStyle.SpreadInside)
    }

    val secondRowConstraints = ConstraintSet {
        val exl = createRefFor("-")
        val at = createRefFor("/")
        val hash = createRefFor(":")
        val dollar = createRefFor(";")
        val perc = createRefFor("(")
        val power = createRefFor(")")
        val amp = createRefFor("$")
        val star = createRefFor("&")
        val braL = createRefFor("@")
        val braR = createRefFor("\"")

        constrain(exl) {
            start.linkTo(parent.start)
            end.linkTo(at.start)
            height = Dimension.value(45.dp)
        }

        constrain(at) {
            start.linkTo(exl.end)
            end.linkTo(hash.start)
            height = Dimension.value(45.dp)
        }

        constrain(hash) {
            start.linkTo(at.end)
            end.linkTo(dollar.start)
            height = Dimension.value(45.dp)
        }

        constrain(dollar) {
            start.linkTo(hash.end)
            end.linkTo(perc.start)
            height = Dimension.value(45.dp)
        }

        constrain(perc) {
            start.linkTo(dollar.end)
            end.linkTo(power.start)
            height = Dimension.value(45.dp)
        }

        constrain(power) {
            start.linkTo(perc.end)
            end.linkTo(amp.start)
            height = Dimension.value(45.dp)
        }

        constrain(amp) {
            start.linkTo(power.end)
            end.linkTo(star.start)
            height = Dimension.value(45.dp)
        }

        constrain(star) {
            start.linkTo(amp.end)
            end.linkTo(braL.start)
            height = Dimension.value(45.dp)
        }

        constrain(braL) {
            start.linkTo(star.end)
            end.linkTo(braR.start)
            height = Dimension.value(45.dp)
        }

        constrain(braR) {
            start.linkTo(braL.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
        }

        createHorizontalChain(exl, at, hash, dollar, perc, power, amp, star, braL, braR, chainStyle = ChainStyle.SpreadInside)
    }

    val thirdRowConstraints = ConstraintSet {
        val symbol = createRefFor("symbol")
        val z = createRefFor(".")
        val x = createRefFor(",")
        val c = createRefFor("?")
        val v = createRefFor("!")
        val b = createRefFor("'")
        val erase = createRefFor("erase")

        constrain(symbol) {
            start.linkTo(parent.start)
            end.linkTo(z.start)
            height = Dimension.value(45.dp)
        }

        constrain(z) {
            start.linkTo(symbol.end)
            end.linkTo(x.start)
            height = Dimension.value(45.dp)
        }

        constrain(x) {
            start.linkTo(z.end)
            end.linkTo(c.start)
            height = Dimension.value(45.dp)
        }

        constrain(c) {
            start.linkTo(x.end)
            end.linkTo(v.start)
            height = Dimension.value(45.dp)
        }

        constrain(v) {
            start.linkTo(c.end)
            end.linkTo(b.start)
            height = Dimension.value(45.dp)
        }

        constrain(b) {
            start.linkTo(v.end)
            end.linkTo(erase.start)
            height = Dimension.value(45.dp)
        }

        constrain(erase) {
            start.linkTo(b.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
        }

        createHorizontalChain(
            symbol,
            z,
            x,
            c,
            v,
            b,
            erase,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val fourthRowConstraints = ConstraintSet {
        val abc = createRefFor("ABC")
        val space = createRefFor("space")
        val ret = createRefFor("action")

        constrain(abc) {
            start.linkTo(parent.start)
            end.linkTo(space.start)
            height = Dimension.value(45.dp)
        }

        constrain(space) {
            start.linkTo(abc.end)
            end.linkTo(ret.start)
            height = Dimension.value(45.dp)
        }

        constrain(ret) {
            start.linkTo(space.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
        }

        createHorizontalChain(
            abc, space, ret,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val language = viewModel.locale.collectAsState("en").value

    ConstraintLayout(constraints, Modifier.width(screenWidth), 100, true) {
        val keyWidth = (screenWidth.value * 0.082).dp
        val keyWidthR3 = (screenWidth.value * 0.11).dp
        val keyWidthB = (screenWidth.value * 0.45).dp
        val keyWidthM = (screenWidth.value * 0.25).dp
        val keyWidthSE = (screenWidth.value * 0.13).dp

        Box(
            Modifier
                .layoutId('1')
        ) {
            ConstraintLayout(
                firstRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), 100, true
            ) {
                if(isSymbol != true) {
                    for (key in row1Keys) KeyboardKey(key, keyWidth, viewModel)
                } else {
                    for (key in row1SymbolKeys) KeyboardKey(key, keyWidth, viewModel)
                }
            }
        }
        Box(
            Modifier
                .layoutId('2')
        ) {
            ConstraintLayout(
                secondRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp), 100, true
            ) {
                if(isSymbol != true) {
                    for (key in row2Keys) KeyboardKey(key, keyWidth, viewModel)
                } else {
                    for (key in row2SymbolKeys) KeyboardKey(key, keyWidth, viewModel)
                }
            }
        }
        Box(
            Modifier
                .layoutId('3')
        ) {
            ConstraintLayout(
                thirdRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                100, true
            ) {
                KeyboardKey(Key("symbol", ""), keyWidthSE, viewModel)
                for (key in row3Keys) KeyboardKey(key, keyWidthR3, viewModel)
                KeyboardKey(Key("erase", ""), keyWidthSE, viewModel)
            }
        }
        Box(
            Modifier
                .layoutId('4')
        ) {
            ConstraintLayout(
                fourthRowConstraints,
                Modifier
                    .width(screenWidth)
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), 100, true
            ) {
                KeyboardKey(Key("ABC", stringResource(R.string.abc)), keyWidthM, viewModel)
                KeyboardKey(Key("space", when(language){
                    "fr" -> "espace"
                    "pt" -> "espaço"
                    "es" -> "espacio"
                    else -> "space"
                }), keyWidthB, viewModel)
                KeyboardKey(Key("action", when(language) {
                    "fr" -> "retour"
                    "pt" -> "retornar"
                    "es" -> "retorno"
                    else -> "return"
                }), keyWidthM, viewModel)
            }
        }
    }
}