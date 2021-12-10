package com.hipoom.scaffold.core.masonry.constraint.size

import android.view.ViewGroup

/**
 * @author ZhengHaiPeng
 * @since 2019-12-06 20:17
 */


infix fun SizeConstraint.dp(dp: Int): SizeConstraint {
    this.value = (owner.resources.displayMetrics.density * dp).toInt()
    return this
}

infix fun SizeConstraint.dp(dp: Float): SizeConstraint {
    this.value = (owner.resources.displayMetrics.density * dp).toInt()
    return this
}

infix fun SizeConstraint.equal(another: SizeConstraint): SizeConstraint {
    this.value = another.value
    return this
}

infix fun SizeConstraint.px(px: Int): SizeConstraint {
    this.value = px
    return this
}

fun SizeConstraint.matchParent(): SizeConstraint {
    this.value = ViewGroup.LayoutParams.MATCH_PARENT
    return this
}

fun SizeConstraint.wrapContent(): SizeConstraint {
    this.value = ViewGroup.LayoutParams.WRAP_CONTENT
    return this
}

fun SizeConstraint.matchConstraint(): SizeConstraint {
    this.value = 0
    return this
}