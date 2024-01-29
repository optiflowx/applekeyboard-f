package com.optiflowx.applekeyboard.views.symbols

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Immutable
class SymbolConstraintSet(
    keyHeight: Dp,
    rowHeight: Dp
) {
    private val percent = 0.086f
    private val percentB = 0.125f
    private val gapW = 0.016f
    private val gapM = 0.035f
    private val pBig = 0.47f
    private val pMedium = 0.25f
    private val pSmall = 0.115f

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

        val gap = createRefFor("gap")
        val gap2 = createRefFor("gap2")
        val gap3 = createRefFor("gap3")
        val gap4 = createRefFor("gap4")
        val gap5 = createRefFor("gap5")
        val gap6 = createRefFor("gap6")
        val gap7 = createRefFor("gap7")
        val gap8 = createRefFor("gap8")
        val gap9 = createRefFor("gap9")

        constrain(p) {
            start.linkTo(parent.start)
            end.linkTo(gap.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(p.end)
            end.linkTo(m.start)
            width = Dimension.percent(gapW)
        }

        constrain(m) {
            start.linkTo(gap.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap2) {
            start.linkTo(m.end)
            end.linkTo(d.start)
            width = Dimension.percent(gapW)
        }

        constrain(d) {
            start.linkTo(gap2.end)
            end.linkTo(gap3.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap3) {
            start.linkTo(d.end)
            end.linkTo(e.start)
            width = Dimension.percent(gapW)
        }

        constrain(e) {
            start.linkTo(gap3.end)
            end.linkTo(gap4.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap4) {
            start.linkTo(e.end)
            end.linkTo(s.start)
            width = Dimension.percent(gapW)
        }

        constrain(s) {
            start.linkTo(gap4.end)
            end.linkTo(gap5.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap5) {
            start.linkTo(s.end)
            end.linkTo(u.start)
            width = Dimension.percent(gapW)
        }

        constrain(u) {
            start.linkTo(gap5.end)
            end.linkTo(gap6.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap6) {
            start.linkTo(u.end)
            end.linkTo(lT.start)
            width = Dimension.percent(gapW)
        }

        constrain(lT) {
            start.linkTo(gap6.end)
            end.linkTo(gap7.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap7) {
            start.linkTo(lT.end)
            end.linkTo(gT.start)
            width = Dimension.percent(gapW)
        }

        constrain(gT) {
            start.linkTo(gap7.end)
            end.linkTo(gap8.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap8) {
            start.linkTo(gT.end)
            end.linkTo(sL.start)
            width = Dimension.percent(gapW)
        }

        constrain(sL) {
            start.linkTo(gap8.end)
            end.linkTo(gap9.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap9) {
            start.linkTo(sL.end)
            end.linkTo(sR.start)
            width = Dimension.percent(gapW)
        }

        constrain(sR) {
            start.linkTo(gap9.end)
            end.linkTo(parent.end)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            p,
            gap,
            m,
            gap2,
            d,
            gap3,
            e,
            gap4,
            s,
            gap5,
            u,
            gap6,
            lT,
            gap7,
            gT,
            gap8,
            sL,
            gap9,
            sR,
            chainStyle = ChainStyle.SpreadInside
        )
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

        val gap = createRefFor("gap")
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")
        val gap3 = createRefFor("gap3")
        val gap4 = createRefFor("gap4")
        val gap5 = createRefFor("gap5")
        val gap6 = createRefFor("gap6")
        val gap7 = createRefFor("gap7")
        val gap8 = createRefFor("gap8")

        constrain(exl) {
            start.linkTo(parent.start)
            end.linkTo(gap.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(exl.end)
            end.linkTo(at.start)
            width = Dimension.percent(gapW)
        }

        constrain(at) {
            start.linkTo(gap.end)
            end.linkTo(gap1.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap1) {
            start.linkTo(at.end)
            end.linkTo(hash.start)
            width = Dimension.percent(gapW)
        }

        constrain(hash) {
            start.linkTo(gap1.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap2) {
            start.linkTo(hash.end)
            end.linkTo(dollar.start)
            width = Dimension.percent(gapW)
        }

        constrain(dollar) {
            start.linkTo(gap2.end)
            end.linkTo(gap3.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap3) {
            start.linkTo(dollar.end)
            end.linkTo(perc.start)
            width = Dimension.percent(gapW)
        }

        constrain(perc) {
            start.linkTo(gap3.end)
            end.linkTo(gap4.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap4) {
            start.linkTo(perc.end)
            end.linkTo(power.start)
            width = Dimension.percent(gapW)
        }

        constrain(power) {
            start.linkTo(gap4.end)
            end.linkTo(gap5.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap5) {
            start.linkTo(power.end)
            end.linkTo(amp.start)
            width = Dimension.percent(gapW)
        }

        constrain(amp) {
            start.linkTo(gap5.end)
            end.linkTo(gap6.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap6) {
            start.linkTo(amp.end)
            end.linkTo(star.start)
            width = Dimension.percent(gapW)
        }

        constrain(star) {
            start.linkTo(gap6.end)
            end.linkTo(gap7.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap7) {
            start.linkTo(star.end)
            end.linkTo(braL.start)
            width = Dimension.percent(gapW)
        }

        constrain(braL) {
            start.linkTo(gap7.end)
            end.linkTo(gap8.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap8) {
            start.linkTo(braL.end)
            end.linkTo(braR.start)
            width = Dimension.percent(gapW)
        }

        constrain(braR) {
            start.linkTo(gap8.end)
            end.linkTo(parent.end)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            exl,
            gap,
            at,
            gap1,
            hash,
            gap2,
            dollar,
            gap3,
            perc,
            gap4,
            power,
            gap5,
            amp,
            gap6,
            star,
            gap7,
            braL,
            gap8,
            braR,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val thirdRowConstraints = ConstraintSet {
        val symbol = createRefFor("symbol")
        val z = createRefFor(".")
        val x = createRefFor(",")
        val c = createRefFor("?")
        val v = createRefFor("!")
        val b = createRefFor("'")
        val delete = createRefFor("delete")

        val gap = createRefFor("gap")
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")
        val gap3 = createRefFor("gap3")
        val gap4 = createRefFor("gap4")
        val gap5 = createRefFor("gap5")

        constrain(symbol) {
            start.linkTo(parent.start)
            end.linkTo(gap.start)
            width = Dimension.percent(pSmall)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(symbol.end)
            end.linkTo(z.start)
            width = Dimension.percent(gapM)
            height = Dimension.value(keyHeight)
        }

        constrain(z) {
            start.linkTo(gap.end)
            end.linkTo(gap1.start)
            width = Dimension.percent(percentB)
            height = Dimension.value(keyHeight)
        }

        constrain(gap1) {
            start.linkTo(z.end)
            end.linkTo(x.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(x) {
            start.linkTo(gap1.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(percentB)
            height = Dimension.value(keyHeight)
        }

        constrain(gap2) {
            start.linkTo(x.end)
            end.linkTo(c.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(c) {
            start.linkTo(gap2.end)
            end.linkTo(gap3.start)
            width = Dimension.percent(percentB)
            height = Dimension.value(keyHeight)
        }

        constrain(gap3) {
            start.linkTo(c.end)
            end.linkTo(v.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(v) {
            start.linkTo(gap3.end)
            end.linkTo(gap4.start)
            width = Dimension.percent(percentB)
            height = Dimension.value(keyHeight)
        }

        constrain(gap4) {
            start.linkTo(v.end)
            end.linkTo(b.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(b) {
            start.linkTo(gap4.end)
            end.linkTo(gap5.end)
            width = Dimension.percent(percentB)
            height = Dimension.value(keyHeight)
        }

        constrain(gap5) {
            start.linkTo(b.end)
            end.linkTo(delete.start)
            width = Dimension.percent(gapM)
            height = Dimension.value(keyHeight)
        }

        constrain(delete) {
            start.linkTo(gap5.end)
            end.linkTo(parent.end)
            width = Dimension.percent(pSmall)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            symbol,
            gap,
            z,
            gap1,
            x,
            gap2,
            c,
            gap3,
            v,
            gap4,
            b,
            gap5,
            delete,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val fourthRowConstraints = ConstraintSet {
        val abc = createRefFor("ABC")
        val emoji = createRefFor("emoji")
        val space = createRefFor("space")
        val action = createRefFor("action")

        val gap = createRefFor("gap")
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")

        constrain(abc) {
            start.linkTo(parent.start)
            end.linkTo(emoji.start)
            width = Dimension.percent(pSmall)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(abc.end)
            end.linkTo(emoji.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(emoji) {
            start.linkTo(gap.end)
            end.linkTo(space.start)
            width = Dimension.percent(pSmall)
            height = Dimension.value(keyHeight)
        }

        constrain(gap1) {
            start.linkTo(emoji.end)
            end.linkTo(space.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(space) {
            start.linkTo(gap.end)
            end.linkTo(gap1.start)
            width = Dimension.percent(pBig)
            height = Dimension.value(keyHeight)
        }

        constrain(gap2) {
            start.linkTo(space.end)
            end.linkTo(action.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(action) {
            start.linkTo(gap2.end)
            end.linkTo(parent.end)
            width = Dimension.percent(pMedium)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            abc, gap, emoji, gap1, space, gap2, action,
            chainStyle = ChainStyle.SpreadInside
        )
    }
}