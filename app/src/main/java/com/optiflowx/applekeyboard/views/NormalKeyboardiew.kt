package com.optiflowx.applekeyboard.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.optiflowx.applekeyboard.adapters.Key
import com.optiflowx.applekeyboard.composables.KeyboardKey

@Composable
fun NormalKeyboardView() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val row1Keys = arrayOf(
        Key("q", "q"),
        Key("w", "w"),
        Key("e", "e"),
        Key("r", "r"),
        Key("t", "t"),
        Key("y", "y"),
        Key("u", "u"),
        Key("i", "i"),
        Key("o", "o"),
        Key("p", "p")
    )

    val row2Keys = arrayOf(
        Key("a", "a"),
        Key("s", "s"),
        Key("d", "d"),
        Key("f", "f"),
        Key("g", "g"),
        Key("h", "h"),
        Key("j", "j"),
        Key("k", "k"),
        Key("l", "l")
    )

    val row3Keys = arrayOf(
        Key("shift", ""),
        Key("z", "z"),
        Key("x", "x"),
        Key("c", "c"),
        Key("v", "v"),
        Key("b", "b"),
        Key("n", "n"),
        Key("m", "m"),
        Key("erase", "")
    )

    val constraints = ConstraintSet {
        val topGuideline = createGuidelineFromTop(18.dp)

        val firstRow = createRefFor('1')
        val secondRow = createRefFor('2')
        val thirdRow = createRefFor('3')
        val fourthRow = createRefFor('4')
//        val fifthRow = createRefFor('5')

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
            height = Dimension.value(45.dp)
        }

        constrain(w) {
            start.linkTo(q.end)
            end.linkTo(e.start)
            height = Dimension.value(45.dp)
        }

        constrain(e) {
            start.linkTo(w.end)
            end.linkTo(r.start)
            height = Dimension.value(45.dp)
        }

        constrain(r) {
            start.linkTo(e.end)
            end.linkTo(t.start)
            height = Dimension.value(45.dp)
        }

        constrain(t) {
            start.linkTo(r.end)
            end.linkTo(y.start)
            height = Dimension.value(45.dp)
        }

        constrain(y) {
            start.linkTo(t.end)
            end.linkTo(u.start)
            height = Dimension.value(45.dp)
        }

        constrain(u) {
            start.linkTo(y.end)
            end.linkTo(i.start)
            height = Dimension.value(45.dp)
        }

        constrain(i) {
            start.linkTo(u.end)
            end.linkTo(o.start)
            height = Dimension.value(45.dp)
        }

        constrain(o) {
            start.linkTo(i.end)
            end.linkTo(p.start)
            height = Dimension.value(45.dp)
        }

        constrain(p) {
            start.linkTo(o.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
        }

        createHorizontalChain(q, w, e, r, t, y, u, i, o, p, chainStyle = ChainStyle.SpreadInside)
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
            height = Dimension.value(45.dp)
        }

        constrain(s) {
            start.linkTo(a.end)
            end.linkTo(d.start)
            height = Dimension.value(45.dp)
        }

        constrain(d) {
            start.linkTo(s.end)
            end.linkTo(f.start)
            height = Dimension.value(45.dp)
        }

        constrain(f) {
            start.linkTo(d.end)
            end.linkTo(g.start)
            height = Dimension.value(45.dp)
        }

        constrain(g) {
            start.linkTo(f.end)
            end.linkTo(h.start)
            height = Dimension.value(45.dp)
        }

        constrain(h) {
            start.linkTo(g.end)
            end.linkTo(j.start)
            height = Dimension.value(45.dp)
        }

        constrain(j) {
            start.linkTo(h.end)
            end.linkTo(k.start)
            height = Dimension.value(45.dp)
        }

        constrain(k) {
            start.linkTo(j.end)
            end.linkTo(l.start)
            height = Dimension.value(45.dp)
        }

        constrain(l) {
            start.linkTo(k.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
        }

        createHorizontalChain(a, s, d, f, g, h, j, k, l, chainStyle = ChainStyle.SpreadInside)
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
            height = Dimension.value(45.dp)
        }

        constrain(z) {
            start.linkTo(shift.end)
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
            end.linkTo(n.start)
            height = Dimension.value(45.dp)
        }

        constrain(n) {
            start.linkTo(b.end)
            end.linkTo(m.start)
            height = Dimension.value(45.dp)
        }

        constrain(m) {
            start.linkTo(n.end)
            end.linkTo(erase.start)
            height = Dimension.value(45.dp)
        }

        constrain(erase) {
            start.linkTo(m.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
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
            height = Dimension.value(45.dp)
        }

        constrain(space) {
            start.linkTo(num.end)
            end.linkTo(ret.start)
            height = Dimension.value(45.dp)
        }

        constrain(ret) {
            start.linkTo(space.end)
            end.linkTo(parent.end)
            height = Dimension.value(45.dp)
        }

        createHorizontalChain(
            num, space, ret,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    ConstraintLayout(constraints, Modifier.width(screenWidth), 100) {
        val keyWidth = (screenWidth.value * 0.082).dp
        val keyWidthB = (screenWidth.value * 0.45).dp
        val keyWidthM = (screenWidth.value * 0.25).dp
        val keyWidthSE = (screenWidth.value * 0.11).dp
        val iconKeyViewPadding = (screenWidth.value * 0.075).dp

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
                    .padding(horizontal = 4.dp), 100
            ) {
                for (key in row1Keys) {
                    KeyboardKey(key, keyWidth)
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
                    .padding(horizontal = 20.dp), 100
            ) {
                for (key in row2Keys) {
                    KeyboardKey(key, keyWidth)
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
                100
            ) {
                for (key in row3Keys) {
                    if(key.id == "shift" || key.id == "erase") {
                        KeyboardKey(key, keyWidthSE)
                    } else {
                        KeyboardKey(key, keyWidth)
                    }
                }
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
                    .padding(horizontal = 4.dp), 100
            ) {
                KeyboardKey(Key("123", "123"), keyWidthM)
                KeyboardKey(Key("space","space"), keyWidthB)
                KeyboardKey(Key("action", "return"), keyWidthM)
            }
        }
    }
}