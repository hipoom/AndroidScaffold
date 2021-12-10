package com.hipoom.scaffold.core.masonry.side

import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintSet


const val SIDE_TOP = ConstraintSet.TOP
const val SIDE_BOTTOM = ConstraintSet.BOTTOM
const val SIDE_LEFT = ConstraintSet.LEFT
const val SIDE_RIGHT = ConstraintSet.RIGHT


@IntDef(SIDE_TOP, SIDE_BOTTOM, SIDE_LEFT, SIDE_RIGHT)
annotation class SideDef