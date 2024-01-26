package com.optiflowx.applekeyboard.views.number

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Immutable
class NumberConstraintsSet {
    private val keyHeight = 55.dp

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


            constrain(firstRow) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(keyHeight)
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
                height = Dimension.value(keyHeight)
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
                height = Dimension.value(keyHeight)
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
                height = Dimension.value(keyHeight)
                width = Dimension.percent(100f)
            }

            constrain(div4) {
                top.linkTo(fourthRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.value(30.dp)
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

        constrain(one) {
            start.linkTo(parent.start)
            end.linkTo(two.start)
            height = Dimension.value(keyHeight)
        }

        constrain(two) {
            start.linkTo(one.end)
            end.linkTo(three.start)
            height = Dimension.value(keyHeight)
        }

        constrain(three) {
            start.linkTo(two.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }
    }

    val secondRowConstraints = ConstraintSet {
        val four = createRefFor('4')
        val five = createRefFor('5')
        val six = createRefFor('6')

        constrain(four) {
            start.linkTo(parent.start)
            end.linkTo(five.start)
            height = Dimension.value(keyHeight)
        }

        constrain(five) {
            start.linkTo(four.end)
            end.linkTo(six.start)
            height = Dimension.value(keyHeight)
        }

        constrain(six) {
            start.linkTo(five.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }
    }

    val thirdRowConstraints = ConstraintSet {
        val seven = createRefFor('7')
        val eight = createRefFor('8')
        val nine = createRefFor('9')

        constrain(seven) {
            start.linkTo(parent.start)
            end.linkTo(eight.start)
            height = Dimension.value(keyHeight)
        }

        constrain(eight) {
            start.linkTo(seven.end)
            end.linkTo(nine.start)
            height = Dimension.value(keyHeight)
        }

        constrain(nine) {
            start.linkTo(eight.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }
    }

    val fourthRowConstraints = ConstraintSet {
        val period = createRefFor(".")
        val zero = createRefFor('0')
        val delete = createRefFor("delete")

        constrain(period) {
            start.linkTo(parent.start)
            end.linkTo(zero.start)
            height = Dimension.value(keyHeight)
        }

        constrain(zero) {
            start.linkTo(period.end)
            end.linkTo(delete.start)
            height = Dimension.value(keyHeight)
        }

        constrain(delete) {
            start.linkTo(zero.end)
            end.linkTo(parent.end)
            height = Dimension.value(keyHeight)
        }
    }
}