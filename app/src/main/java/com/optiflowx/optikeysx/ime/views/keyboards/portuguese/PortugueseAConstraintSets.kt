package com.optiflowx.optikeysx.ime.views.keyboards.portuguese

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Immutable
class PortugueseAConstraintSets(
    keyHeight: Dp,
    rowHeight: Dp,
) {
    private val percent = 0.086f
    private val gapW = 0.013f
    private val gapM = 0.034f
    private val pBig = 0.47f
    private val pMedium = 0.25f
    private val pSmall = 0.115f

    val constraints by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        ConstraintSet {
            val firstRow = createRefFor('1')
            val secondRow = createRefFor('2')
            val thirdRow = createRefFor('3')
            val fourthRow = createRefFor('4')
            val topGuideline = createGuidelineFromTop(10.dp)

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
        val q = createRefFor("q")
        val w = createRefFor("w")
        val e = createRefFor("e")
        val r = createRefFor("r")
        val t = createRefFor("t")
        val y = createRefFor("y")
        val u = createRefFor("u")
        val i = createRefFor("i")
        val o = createRefFor("o")
        val p = createRefFor("p")

        val gap = createRefFor("gap")
        val gap2 = createRefFor("gap2")
        val gap3 = createRefFor("gap3")
        val gap4 = createRefFor("gap4")
        val gap5 = createRefFor("gap5")
        val gap6 = createRefFor("gap6")
        val gap7 = createRefFor("gap7")
        val gap8 = createRefFor("gap8")
        val gap9 = createRefFor("gap9")

        constrain(q) {
            start.linkTo(parent.start)
            end.linkTo(gap.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(q.end)
            end.linkTo(w.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(w) {
            start.linkTo(gap.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap2) {
            start.linkTo(w.end)
            end.linkTo(e.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(e) {
            start.linkTo(gap2.end)
            end.linkTo(gap3.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap3) {
            start.linkTo(e.end)
            end.linkTo(r.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(r) {
            start.linkTo(gap3.end)
            end.linkTo(gap4.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap4) {
            start.linkTo(r.end)
            end.linkTo(t.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(t) {
            start.linkTo(gap4.end)
            end.linkTo(gap5.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap5) {
            start.linkTo(t.end)
            end.linkTo(y.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(y) {
            start.linkTo(gap5.end)
            end.linkTo(gap6.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap6) {
            start.linkTo(y.end)
            end.linkTo(u.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(u) {
            start.linkTo(gap6.end)
            end.linkTo(gap7.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap7) {
            start.linkTo(u.end)
            end.linkTo(i.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(i) {
            start.linkTo(gap7.end)
            end.linkTo(gap8.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap8) {
            start.linkTo(i.end)
            end.linkTo(o.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(o) {
            start.linkTo(gap8.end)
            end.linkTo(p.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap9) {
            start.linkTo(o.end)
            end.linkTo(p.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(p) {
            start.linkTo(gap9.end)
            end.linkTo(parent.end)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            q,
            gap,
            w,
            gap2,
            e,
            gap3,
            r,
            gap4,
            t,
            gap5,
            y,
            gap6,
            u,
            gap7,
            i,
            gap8,
            o,
            gap9,
            p,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val secondRowConstraints = ConstraintSet {
        val a = createRefFor("a")
        val s = createRefFor("s")
        val d = createRefFor("d")
        val f = createRefFor("f")
        val g = createRefFor("g")
        val h = createRefFor("h")
        val j = createRefFor("j")
        val k = createRefFor("k")
        val l = createRefFor("l")
        val c = createRefFor("ç")

        val gap = createRefFor("gap")
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")
        val gap3 = createRefFor("gap3")
        val gap4 = createRefFor("gap4")
        val gap5 = createRefFor("gap5")
        val gap6 = createRefFor("gap6")
        val gap7 = createRefFor("gap7")
        val gap8 = createRefFor("gap8")

        constrain(a) {
            start.linkTo(parent.start)
            end.linkTo(gap.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(a.end)
            end.linkTo(s.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(s) {
            start.linkTo(gap.end)
            end.linkTo(gap1.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap1) {
            start.linkTo(s.end)
            end.linkTo(d.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(d) {
            start.linkTo(gap1.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap2) {
            start.linkTo(d.end)
            end.linkTo(gap3.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(f) {
            start.linkTo(gap2.end)
            end.linkTo(gap3.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap3) {
            start.linkTo(f.end)
            end.linkTo(gap4.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(g) {
            start.linkTo(gap3.end)
            end.linkTo(gap4.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap4) {
            start.linkTo(g.end)
            end.linkTo(gap5.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(h) {
            start.linkTo(gap4.end)
            end.linkTo(gap5.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap5) {
            start.linkTo(h.end)
            end.linkTo(gap6.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(j) {
            start.linkTo(gap5.end)
            end.linkTo(gap6.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap6) {
            start.linkTo(j.end)
            end.linkTo(gap7.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(k) {
            start.linkTo(gap6.end)
            end.linkTo(gap7.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap7) {
            start.linkTo(k.end)
            end.linkTo(l.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(l) {
            start.linkTo(gap7.end)
            end.linkTo(gap8.end)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap8) {
            start.linkTo(l.end)
            end.linkTo(c.end)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(c) {
            start.linkTo(gap8.end)
            end.linkTo(parent.end)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            a,
            gap,
            s,
            gap1,
            d,
            gap2,
            f,
            gap3,
            g,
            gap4,
            h,
            gap5,
            j,
            gap6,
            k,
            gap7,
            l,
            gap8,
            c,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val thirdRowConstraints = ConstraintSet {

        val shift = createRefFor("shift")
        val z = createRefFor("z")
        val x = createRefFor("x")
        val c = createRefFor("c")
        val v = createRefFor("v")
        val b = createRefFor("b")
        val n = createRefFor("n")
        val m = createRefFor("m")
        val delete = createRefFor("delete")

        val gap = createRefFor("gap")
        val gap2 = createRefFor("gap2")
        val gap3 = createRefFor("gap3")
        val gap4 = createRefFor("gap4")
        val gap5 = createRefFor("gap5")
        val gap6 = createRefFor("gap6")
        val gap7 = createRefFor("gap7")
        val gap8 = createRefFor("gap8")

        constrain(shift) {
            start.linkTo(parent.start)
            end.linkTo(gap.start)
            width = Dimension.percent(pSmall)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(shift.end)
            end.linkTo(z.start)
            width = Dimension.percent(gapM)
            height = Dimension.value(keyHeight)
        }

        constrain(z) {
            start.linkTo(gap.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap2) {
            start.linkTo(z.end)
            end.linkTo(x.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(x) {
            start.linkTo(gap2.end)
            end.linkTo(gap3.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap3) {
            start.linkTo(x.end)
            end.linkTo(c.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(c) {
            start.linkTo(gap3.end)
            end.linkTo(gap4.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap4) {
            start.linkTo(c.end)
            end.linkTo(v.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(v) {
            start.linkTo(gap4.end)
            end.linkTo(gap5.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap5) {
            start.linkTo(v.end)
            end.linkTo(b.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(b) {
            start.linkTo(gap5.end)
            end.linkTo(gap6.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap6) {
            start.linkTo(b.end)
            end.linkTo(n.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(n) {
            start.linkTo(gap6.end)
            end.linkTo(gap7.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap7) {
            start.linkTo(n.end)
            end.linkTo(m.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(m) {
            start.linkTo(gap7.end)
            end.linkTo(gap8.start)
            width = Dimension.percent(percent)
            height = Dimension.value(keyHeight)
        }

        constrain(gap8) {
            start.linkTo(m.end)
            end.linkTo(delete.start)
            width = Dimension.percent(gapM)
            height = Dimension.value(keyHeight)
        }

        constrain(delete) {
            start.linkTo(gap8.end)
            end.linkTo(parent.end)
            width = Dimension.percent(pSmall)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            shift,
            gap,
            z,
            gap2,
            x,
            gap3,
            c,
            gap4,
            v,
            gap5,
            b,
            gap6,
            n,
            gap7,
            m,
            gap8,
            delete,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val fourthRowConstraints = ConstraintSet {
        val num = createRefFor("123")
        val emoji = createRefFor("emoji")
        val space = createRefFor("space")
        val ret = createRefFor("action")

        val gap = createRefFor("gap")
        val gap2 = createRefFor("gap2")
        val gap3 = createRefFor("gap3")

        constrain(num) {
            start.linkTo(parent.start)
            end.linkTo(gap.start)
            width = Dimension.percent(pSmall)
            height = Dimension.value(keyHeight)
        }

        constrain(gap) {
            start.linkTo(num.end)
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

        constrain(gap2) {
            start.linkTo(emoji.end)
            end.linkTo(space.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(space) {
            start.linkTo(gap.end)
            end.linkTo(ret.start)
            width = Dimension.percent(pBig)
            height = Dimension.value(keyHeight)
        }

        constrain(gap3) {
            start.linkTo(space.end)
            end.linkTo(ret.start)
            width = Dimension.percent(gapW)
            height = Dimension.value(keyHeight)
        }

        constrain(ret) {
            start.linkTo(gap.end)
            end.linkTo(parent.end)
            width = Dimension.percent(pMedium)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            num, gap, emoji, gap2, space, gap3, ret,
            chainStyle = ChainStyle.SpreadInside
        )
    }
}