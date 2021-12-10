package com.hipoom.scaffold.core.masonry.constraint.vertical

import android.view.View
import com.hipoom.scaffold.core.masonry.constraint.side.toBottomOf
import com.hipoom.scaffold.core.masonry.constraint.side.toTopOf


infix fun VerticalConstraint.of(view: View): VerticalConstraint {
    make.top toTopOf view
    make.bottom toBottomOf view
    return this
}

infix fun VerticalConstraint.margin(px: Int): VerticalConstraint {
    make.top.margin = px
    make.bottom.margin = px
    return this
}

infix fun VerticalConstraint.center(view: View) = of(view)