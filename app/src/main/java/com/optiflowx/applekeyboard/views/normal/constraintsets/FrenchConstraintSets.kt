package com.optiflowx.applekeyboard.views.normal.constraintsets

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Immutable
class FrenchConstraintSets(keyHeight: Dp, rowHeight: Dp) {

    val constraints by lazy(LazyThreadSafetyMode.NONE) {
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
        val a = createRefFor("a")
        val z = createRefFor("z")
        val e = createRefFor("e")
        val r = createRefFor("r")
        val t = createRefFor("t")
        val y = createRefFor("y")
        val u = createRefFor("u")
        val i = createRefFor("i")
        val o = createRefFor("o")
        val p = createRefFor("p")

        constrain(a) {
            start.linkTo(parent.start)
            end.linkTo(z.start)
            height = Dimension.value(keyHeight)
        }

        constrain(z) {
            start.linkTo(a.end)
            end.linkTo(e.start)
            height = Dimension.value(keyHeight)
        }

        constrain(e) {
            start.linkTo(z.end)
            end.linkTo(r.start)
            height = Dimension.value(keyHeight)
        }

        constrain(r) {
            start.linkTo(e.end)
            end.linkTo(t.start)
            height = Dimension.value(keyHeight)
        }

        constrain(t) {
            start.linkTo(r.end)
            end.linkTo(y.start)
            height = Dimension.value(keyHeight)
        }

        constrain(y) {
            start.linkTo(t.end)
            end.linkTo(u.start)
            height = Dimension.value(keyHeight)
        }

        constrain(u) {
            start.linkTo(y.end)
            end.linkTo(i.start)
            height = Dimension.value(keyHeight)
        }

        constrain(i) {
            start.linkTo(u.end)
            end.linkTo(o.start)
            height = Dimension.value(keyHeight)
        }

        constrain(o) {
            start.linkTo(i.end)
            end.linkTo(p.start)
            height = Dimension.value(keyHeight)
        }

        constrain(p) {
            start.linkTo(o.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(a, z, e, r, t, y, u, i, o, p, chainStyle = ChainStyle.SpreadInside)
    }

    val secondRowConstraints = ConstraintSet {
        //Create Ids for arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L")
        val q = createRefFor("q")
        val s = createRefFor("s")
        val d = createRefFor("d")
        val f = createRefFor("f")
        val g = createRefFor("g")
        val h = createRefFor("h")
        val j = createRefFor("j")
        val k = createRefFor("k")
        val l = createRefFor("l")

        constrain(q) {
            start.linkTo(parent.start)
            end.linkTo(s.start)
            height = Dimension.value(keyHeight)
        }

        constrain(s) {
            start.linkTo(q.end)
            end.linkTo(d.start)
            height = Dimension.value(keyHeight)
        }

        constrain(d) {
            start.linkTo(s.end)
            end.linkTo(f.start)
            height = Dimension.value(keyHeight)
        }

        constrain(f) {
            start.linkTo(d.end)
            end.linkTo(g.start)
            height = Dimension.value(keyHeight)
        }

        constrain(g) {
            start.linkTo(f.end)
            end.linkTo(h.start)
            height = Dimension.value(keyHeight)
        }

        constrain(h) {
            start.linkTo(g.end)
            end.linkTo(j.start)
            height = Dimension.value(keyHeight)
        }

        constrain(j) {
            start.linkTo(h.end)
            end.linkTo(k.start)
            height = Dimension.value(keyHeight)
        }

        constrain(k) {
            start.linkTo(j.end)
            end.linkTo(l.start)
            height = Dimension.value(keyHeight)
        }

        constrain(l) {
            start.linkTo(k.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(q, s, d, f, g, h, j, k, l, chainStyle = ChainStyle.SpreadInside)
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

        constrain(shift) {
            start.linkTo(parent.start)
            end.linkTo(z.start)
            height = Dimension.value(keyHeight)
        }

        constrain(z) {
            start.linkTo(shift.end)
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
            end.linkTo(n.start)
            height = Dimension.value(keyHeight)
        }

        constrain(n) {
            start.linkTo(b.end)
            end.linkTo(m.start)
            height = Dimension.value(keyHeight)
        }

        constrain(m) {
            start.linkTo(n.end)
            end.linkTo(delete.start)
            height = Dimension.value(keyHeight)
        }

        constrain(delete) {
            start.linkTo(m.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            shift,
            z,
            x,
            c,
            v,
            b,
            n,
            m,
            delete,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val fourthRowConstraints = ConstraintSet {
        val num = createRefFor("123")
        val emoji = createRefFor("emoji")
        val space = createRefFor("space")
        val ret = createRefFor("action")

        constrain(num) {
            start.linkTo(parent.start)
            end.linkTo(emoji.start)
            height = Dimension.value(keyHeight)
        }

        constrain(emoji) {
            start.linkTo(num.end)
            end.linkTo(space.start)
            height = Dimension.value(keyHeight)
        }

        constrain(space) {
            start.linkTo(num.end)
            end.linkTo(ret.start)
            height = Dimension.value(keyHeight)
        }

        constrain(ret) {
            start.linkTo(space.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(
            num, emoji, space, ret,
            chainStyle = ChainStyle.SpreadInside
        )
    }
}