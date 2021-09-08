package me.haipeng.scaffold.core.masonry.side

import android.view.View
import me.haipeng.scaffold.core.masonry.side.*


val View.topSide
    get() = Side(SIDE_TOP, this)

val View.bottomSide
    get() = Side(SIDE_BOTTOM, this)

val View.leftSide
    get() = Side(SIDE_LEFT, this)

val View.rightSide
    get() = Side(SIDE_RIGHT, this)

val View.startSide
    get() = Side(SIDE_LEFT, this)

val View.endSide
    get() = Side(SIDE_RIGHT, this)
