package com.hipoom.scaffold.core.masonry.constraint.horizontal

import android.view.View
import com.moolv.masonry.constraint.horizontal.HorizontalConstraint
import com.hipoom.scaffold.core.masonry.constraint.side.toEndOf
import com.hipoom.scaffold.core.masonry.constraint.side.toStartOf

/**
 * @author ZhengHaiPeng
 * @since 2019-12-06 20:15
 *
 */



infix fun HorizontalConstraint.of(view: View): HorizontalConstraint {
    make.start toStartOf view
    make.end toEndOf view
    return this
}

infix fun HorizontalConstraint.margin(px: Int): HorizontalConstraint {
    make.start.margin = px
    make.end.margin = px
    return this
}

infix fun HorizontalConstraint.center(view: View) = of(view)
