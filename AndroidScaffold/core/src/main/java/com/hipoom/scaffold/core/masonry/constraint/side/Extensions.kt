package com.hipoom.scaffold.core.masonry.constraint.side

import android.view.View
import com.hipoom.scaffold.core.masonry.side.*

/**
 * @author ZhengHaiPeng
 * @since 2019-12-06 20:11
 *
 */



@Deprecated("容易和 Pair 的 to 扩展混淆，放弃使用。改为用  toStartOf、toEndOf、toTopOf、toBottomOf 替代")
infix fun OneSideConstraint.to(view: View): OneSideConstraint {
    this.target = view
    this.targetSide = this.ownerSide
    return this
}

infix fun OneSideConstraint.to(side: Side): OneSideConstraint {
    this.target = side.owner
    this.targetSide = side.type
    return this
}

infix fun OneSideConstraint.margin(px: Int): OneSideConstraint {
    this.margin = px
    return this
}




infix fun HorizontalSideConstraint.toStartOf(view: View): HorizontalSideConstraint {
    this.target = view
    this.targetSide = view.leftSide.type
    return this
}

infix fun VerticalSideConstraint.toTopOf(view: View): VerticalSideConstraint {
    this.target = view
    this.targetSide = view.topSide.type
    return this
}

infix fun HorizontalSideConstraint.toEndOf(view: View): HorizontalSideConstraint {
    this.target = view
    this.targetSide = view.endSide.type
    return this
}

infix fun VerticalSideConstraint.toBottomOf(view: View): VerticalSideConstraint {
    this.target = view
    this.targetSide = view.bottomSide.type
    return this
}



infix fun Array<OneSideConstraint>.alignTo(view: View) {
    forEach {
        it to view
    }
}