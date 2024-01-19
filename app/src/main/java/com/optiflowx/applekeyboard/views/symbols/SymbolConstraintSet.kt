package com.optiflowx.applekeyboard.views.symbols

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Immutable
class SymbolConstraintSet {
    private val keyHeight = 44.dp
    private val rowHeight = 56.dp

    val main by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        ConstraintSet {
            val topGuideline = createGuidelineFromTop(10.dp)

            val firstRow = createRefFor('1')
            val secondRow = createRefFor('2')
            val thirdRow = createRefFor('3')
            val fourthRow = createRefFor('4')

            constrain(firstRow) {
                top.linkTo(topGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight)
                width = Dimension.percent(100f)
            }

            constrain(secondRow) {
                top.linkTo(firstRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight)
                width = Dimension.percent(100f)
            }

            constrain(thirdRow) {
                top.linkTo(secondRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight)
                width = Dimension.percent(100f)
            }

            constrain(fourthRow) {
                top.linkTo(thirdRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight)
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
            height = Dimension.value(keyHeight)
        }

        constrain(m) {
            start.linkTo(p.end)
            end.linkTo(d.start)
            height = Dimension.value(keyHeight)
        }

        constrain(d) {
            start.linkTo(m.end)
            end.linkTo(e.start)
            height = Dimension.value(keyHeight)
        }

        constrain(e) {
            start.linkTo(d.end)
            end.linkTo(s.start)
            height = Dimension.value(keyHeight)
        }

        constrain(s) {
            start.linkTo(e.end)
            end.linkTo(u.start)
            height = Dimension.value(keyHeight)
        }

        constrain(u) {
            start.linkTo(s.end)
            end.linkTo(lT.start)
            height = Dimension.value(keyHeight)
        }

        constrain(lT) {
            start.linkTo(u.end)
            end.linkTo(gT.start)
            height = Dimension.value(keyHeight)
        }

        constrain(gT) {
            start.linkTo(lT.end)
            end.linkTo(sL.start)
            height = Dimension.value(keyHeight)
        }

        constrain(sL) {
            start.linkTo(gT.end)
            end.linkTo(sR.start)
            height = Dimension.value(keyHeight)
        }

        constrain(sR) {
            start.linkTo(sL.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
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
            height = Dimension.value(keyHeight)
        }

        constrain(at) {
            start.linkTo(exl.end)
            end.linkTo(hash.start)
            height = Dimension.value(keyHeight)
        }

        constrain(hash) {
            start.linkTo(at.end)
            end.linkTo(dollar.start)
            height = Dimension.value(keyHeight)
        }

        constrain(dollar) {
            start.linkTo(hash.end)
            end.linkTo(perc.start)
            height = Dimension.value(keyHeight)
        }

        constrain(perc) {
            start.linkTo(dollar.end)
            end.linkTo(power.start)
            height = Dimension.value(keyHeight)
        }

        constrain(power) {
            start.linkTo(perc.end)
            end.linkTo(amp.start)
            height = Dimension.value(keyHeight)
        }

        constrain(amp) {
            start.linkTo(power.end)
            end.linkTo(star.start)
            height = Dimension.value(keyHeight)
        }

        constrain(star) {
            start.linkTo(amp.end)
            end.linkTo(braL.start)
            height = Dimension.value(keyHeight)
        }

        constrain(braL) {
            start.linkTo(star.end)
            end.linkTo(braR.start)
            height = Dimension.value(keyHeight)
        }

        constrain(braR) {
            start.linkTo(braL.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
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
            height = Dimension.value(keyHeight)
        }

        constrain(z) {
            start.linkTo(symbol.end)
            end.linkTo(x.start)
            height = Dimension.value(keyHeight)
        }

        constrain(x) {
            start.linkTo(z.end)
            end.linkTo(c.start)
            height = Dimension.value(keyHeight)
        }

        constrain(c) {
            start.linkTo(x.end)
            end.linkTo(v.start)
            height = Dimension.value(keyHeight)
        }

        constrain(v) {
            start.linkTo(c.end)
            end.linkTo(b.start)
            height = Dimension.value(keyHeight)
        }

        constrain(b) {
            start.linkTo(v.end)
            end.linkTo(erase.start)
            height = Dimension.value(keyHeight)
        }

        constrain(erase) {
            start.linkTo(b.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
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
        val action = createRefFor("action")

        constrain(abc) {
            start.linkTo(parent.start)
            end.linkTo(space.start)
            height = Dimension.value(keyHeight)
        }

        constrain(space) {
            start.linkTo(abc.end)
            end.linkTo(action.start)
            height = Dimension.value(keyHeight)
        }

        constrain(action) {
            start.linkTo(space.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            abc, space, action,
            chainStyle = ChainStyle.SpreadInside
        )
    }
}