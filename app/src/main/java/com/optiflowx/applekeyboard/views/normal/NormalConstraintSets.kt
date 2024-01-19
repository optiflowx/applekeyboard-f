package com.optiflowx.applekeyboard.views.normal

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Immutable
class NormalConstraintSets {
    private val keyHeight = 44.dp
    private val rowHeight = 56.dp

    val mainColumnConstraints by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
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
        //Create Ids for arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")
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

        constrain(q) {
            start.linkTo(parent.start)
            end.linkTo(w.start)
            height = Dimension.value(keyHeight)
        }

        constrain(w) {
            start.linkTo(q.end)
            end.linkTo(e.start)
            height = Dimension.value(keyHeight)
        }

        constrain(e) {
            start.linkTo(w.end)
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

        createHorizontalChain(q, w, e, r, t, y, u, i, o, p, chainStyle = ChainStyle.SpreadInside)
    }

    val firstRowConstraintsFr = ConstraintSet {
        //Create Ids for arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")
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
        val a = createRefFor("a")
        val s = createRefFor("s")
        val d = createRefFor("d")
        val f = createRefFor("f")
        val g = createRefFor("g")
        val h = createRefFor("h")
        val j = createRefFor("j")
        val k = createRefFor("k")
        val l = createRefFor("l")

        constrain(a) {
            start.linkTo(parent.start)
            end.linkTo(s.start)
            height = Dimension.value(keyHeight)
        }

        constrain(s) {
            start.linkTo(a.end)
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

        createHorizontalChain(a, s, d, f, g, h, j, k, l, chainStyle = ChainStyle.SpreadInside)
    }

    val secondRowConstraintsSP = ConstraintSet {
        //Create Ids for arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L")
        val a = createRefFor("a")
        val s = createRefFor("s")
        val d = createRefFor("d")
        val f = createRefFor("f")
        val g = createRefFor("g")
        val h = createRefFor("h")
        val j = createRefFor("j")
        val k = createRefFor("k")
        val l = createRefFor("l")
        val n = createRefFor("ñ")

        constrain(a) {
            start.linkTo(parent.start)
            end.linkTo(s.start)
            height = Dimension.value(keyHeight)
        }

        constrain(s) {
            start.linkTo(a.end)
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
            end.linkTo(n.start)
            height = Dimension.value(keyHeight)
        }

        constrain(n) {
            start.linkTo(l.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }

        createHorizontalChain(a, s, d, f, g, h, j, k, l, n, chainStyle = ChainStyle.SpreadInside)
    }

    val secondRowConstraintsFr = ConstraintSet {
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
        //Create Ids for arrayOf(z, x, c, v, b, n, m)
        val shift = createRefFor("shift")
        val z = createRefFor("z")
        val x = createRefFor("x")
        val c = createRefFor("c")
        val v = createRefFor("v")
        val b = createRefFor("b")
        val n = createRefFor("n")
        val m = createRefFor("m")
        val erase = createRefFor("erase")

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
            end.linkTo(erase.start)
            height = Dimension.value(keyHeight)
        }

        constrain(erase) {
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
            erase,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val thirdRowConstraintsFr = ConstraintSet {
        //Create Ids for arrayOf(z, x, c, v, b, n, m)
        val shift = createRefFor("shift")
        val z = createRefFor("z")
        val x = createRefFor("x")
        val c = createRefFor("c")
        val v = createRefFor("v")
        val b = createRefFor("b")
        val n = createRefFor("n")
        val m = createRefFor("m")
        val erase = createRefFor("erase")

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
            end.linkTo(erase.start)
            height = Dimension.value(keyHeight)
        }

        constrain(erase) {
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
            erase,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val fourthRowConstraints = ConstraintSet {
        val num = createRefFor("123")
        val space = createRefFor("space")
        val ret = createRefFor("action")

        constrain(num) {
            start.linkTo(parent.start)
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
            num, space, ret,
            chainStyle = ChainStyle.SpreadInside
        )
    }
}