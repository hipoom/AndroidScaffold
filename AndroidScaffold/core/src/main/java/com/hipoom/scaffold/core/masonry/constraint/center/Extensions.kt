@file:Suppress("PackageDirectoryMismatch")

package com.moolv.masonry.constraint.center

import android.view.View
import com.hipoom.scaffold.core.masonry.constraint.center.CenterConstraint
import com.hipoom.scaffold.core.masonry.constraint.side.to

infix fun CenterConstraint.of(view: View): CenterConstraint {
    make.start to view
    make.end to view
    make.top to view
    make.bottom to view
    return this
}

infix fun CenterConstraint.margin(px: Int): CenterConstraint {
    make.start.margin = px
    make.end.margin = px
    make.top.margin = px
    make.bottom.margin = px
    return this
}