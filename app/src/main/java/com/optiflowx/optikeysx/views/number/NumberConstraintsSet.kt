package com.optiflowx.optikeysx.views.number

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Immutable
class NumberConstraintsSet(
    rowHeight: Int,
    bottomDivHeight: Int,
) {
    private val gapW = 0.014f
    private val keyWidth = 0.32f
    private val keyHeight = rowHeight - 1

    val constraints by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        ConstraintSet {
            val firstRow = createRefFor('1')
            val secondRow = createRefFor('2')
            val thirdRow = createRefFor('3')
            val fourthRow = createRefFor('4')
            val div1 = createRefFor("div1")
            val div2 = createRefFor("div2")
            val div3 = createRefFor("div3")
            val div4 = createRefFor("div4")
            val divHeight = 5.dp

            constrain(firstRow) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight.dp)
                width = Dimension.percent(100f)
            }

            constrain(div1) {
                top.linkTo(firstRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(divHeight)
                width = Dimension.percent(100f)
            }

            constrain(secondRow) {
                top.linkTo(div1.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight.dp)
                width = Dimension.percent(100f)
            }

            constrain(div2) {
                top.linkTo(secondRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(divHeight)
                width = Dimension.percent(100f)
            }

            constrain(thirdRow) {
                top.linkTo(div2.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight.dp)
                width = Dimension.percent(100f)
            }

            constrain(div3) {
                top.linkTo(thirdRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(divHeight)
                width = Dimension.percent(100f)
            }

            constrain(fourthRow) {
                top.linkTo(div3.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(rowHeight.dp)
                width = Dimension.percent(100f)
            }

            constrain(div4) {
                top.linkTo(fourthRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(bottomDivHeight.dp)
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
                div4,
                chainStyle = ChainStyle.Packed
            )
        }
    }

    val firstRowConstraints = ConstraintSet {
        val one = createRefFor('1')
        val two = createRefFor('2')
        val three = createRefFor('3')
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")

        constrain(one) {
            start.linkTo(parent.start)
            end.linkTo(gap1.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap1) {
            start.linkTo(one.end)
            end.linkTo(two.start)
            width = Dimension.percent(gapW)
        }

        constrain(two) {
            start.linkTo(gap1.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap2) {
            start.linkTo(two.end)
            end.linkTo(three.start)
            width = Dimension.percent(gapW)
        }

        constrain(three) {
            start.linkTo(gap2.end)
            end.linkTo(parent.end)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        createHorizontalChain(
            one,
            gap1,
            two,
            gap2,
            three,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val secondRowConstraints = ConstraintSet {
        val four = createRefFor('4')
        val five = createRefFor('5')
        val six = createRefFor('6')
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")

        constrain(four) {
            start.linkTo(parent.start)
            end.linkTo(gap1.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap1) {
            start.linkTo(four.end)
            end.linkTo(five.start)
            width = Dimension.percent(gapW)
        }

        constrain(five) {
            start.linkTo(gap1.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap2) {
            start.linkTo(five.end)
            end.linkTo(six.start)
            width = Dimension.percent(gapW)
        }

        constrain(six) {
            start.linkTo(gap2.end)
            end.linkTo(parent.end)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        createHorizontalChain(
            four,
            gap1,
            five,
            gap2,
            six,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val thirdRowConstraints = ConstraintSet {
        val seven = createRefFor('7')
        val eight = createRefFor('8')
        val nine = createRefFor('9')
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")

        constrain(seven) {
            start.linkTo(parent.start)
            end.linkTo(gap1.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap1) {
            start.linkTo(seven.end)
            end.linkTo(eight.start)

            width = Dimension.percent(gapW)
        }

        constrain(eight) {
            start.linkTo(gap1.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap2) {
            start.linkTo(eight.end)
            end.linkTo(nine.start)
            width = Dimension.percent(gapW)
        }

        constrain(nine) {
            start.linkTo(gap2.end)
            end.linkTo(parent.end)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        createHorizontalChain(
            seven,
            gap1,
            eight,
            gap2,
            nine,
            chainStyle = ChainStyle.SpreadInside
        )
    }

    val fourthRowConstraints = ConstraintSet {
        val period = createRefFor("period")
        val zero = createRefFor('0')
        val delete = createRefFor("delete")
        val gap1 = createRefFor("gap1")
        val gap2 = createRefFor("gap2")

        constrain(period) {
            start.linkTo(parent.start)
            end.linkTo(gap1.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap1) {
            start.linkTo(period.end)
            end.linkTo(zero.start)
            width = Dimension.percent(gapW)
        }

        constrain(zero) {
            start.linkTo(gap1.end)
            end.linkTo(gap2.start)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        constrain(gap2) {
            start.linkTo(zero.end)
            end.linkTo(delete.start)
            width = Dimension.percent(gapW)
        }

        constrain(delete) {
            start.linkTo(gap2.end)
            end.linkTo(parent.end)
            width = Dimension.percent(keyWidth)
            height = Dimension.value(keyHeight.dp)
        }

        createHorizontalChain(
            period,
            gap1,
            zero,
            gap2,
            delete,
            chainStyle = ChainStyle.SpreadInside
        )
    }
}