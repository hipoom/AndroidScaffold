@file:Suppress("unused", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")

package com.hipoom.scaffold.core.masonry

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hipoom.scaffold.core.masonry.constraint.center.CenterConstraint
import com.moolv.masonry.constraint.horizontal.HorizontalConstraint
import com.hipoom.scaffold.core.masonry.constraint.horizontal.center
import com.hipoom.scaffold.core.masonry.constraint.side.*
import com.hipoom.scaffold.core.masonry.constraint.size.SizeConstraint
import com.hipoom.scaffold.core.masonry.constraint.size.wrapContent
import com.hipoom.scaffold.core.masonry.constraint.vertical.VerticalConstraint
import com.hipoom.scaffold.core.masonry.constraint.vertical.center

/**
 * @author ZhengHaiPeng
 * @since 2019-12-06 20:17
 *
 */
class ConstraintMake(val owner: View) {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    val top = TopSideConstraint(owner)
    val bottom = BottomSideConstraint(owner)
    val left = LeftSideConstraint(owner)
    val right = RightSideConstraint(owner)

    val width = SizeConstraint(owner)
    val height = SizeConstraint(owner)

    val parent: ConstraintLayout
    val start = left
    val end = right

    val center = CenterConstraint(this)

    val horizontal = HorizontalConstraint(this)
    val vertical = VerticalConstraint(this)



    /* ======================================================= */
    /* Constructors                                            */
    /* ======================================================= */

    init {
        val p = owner.parent as? ConstraintLayout ?:
        throw IllegalStateException("$owner 没有父布局或者父布局不是 ConstraintLayout")
        parent = p
    }



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 在 [view] 的正下方
     */
    infix fun below(view: View) = outerBottomCenter(view)

    /**
     * 在 [view] 的正上方
     */
    infix fun above(view: View) = outerTopCenter(view)



    /* ======================================================= */
    /* Inner + Center                                          */
    /* ======================================================= */

    /**
     * 在 [view] 内部的左侧，竖直方向居中
     */
    infix fun innerLeftCenter(view: View): LeftSideConstraint {
        vertical center view
        left toStartOf view
        return left
    }

    /**
     * 在 [view] 内部的上部，水平方向居中
     */
    infix fun innerTopCenter(view: View): TopSideConstraint {
        horizontal center view
        top toTopOf view
        return top
    }

    /**
     * 在 [view] 内部的右侧，竖直方向居中
     */
    infix fun innerRightCenter(view: View): RightSideConstraint {
        vertical center view
        right toEndOf view
        return right
    }

    /**
     * 在 [view] 内部的底部，水平方向居中
     */
    infix fun innerBottomCenter(view: View): BottomSideConstraint {
        horizontal center view
        bottom toBottomOf view
        return bottom
    }



    /* ======================================================= */
    /* Outer + Center                                          */
    /* ======================================================= */

    /**
     * 在 [view] 外部的左侧，竖直方向居中
     */
    infix fun outerLeftCenter(view: View): RightSideConstraint {
        vertical center view
        right toStartOf view
        return right
    }

    /**
     * 在 [view] 外部的上部，水平方向居中
     */
    infix fun outerTopCenter(view: View): BottomSideConstraint {
        horizontal center view
        bottom toTopOf view
        return bottom
    }

    /**
     * 在 [view] 外部的右侧，竖直方向居中
     */
    infix fun outerRightCenter(view: View): LeftSideConstraint {
        vertical center view
        start toEndOf view
        return start
    }

    /**
     * 在 [view] 外部的底部，水平方向居中
     */
    infix fun outerBottomCenter(view: View): TopSideConstraint {
        horizontal center view
        top toBottomOf view
        return top
    }


    /**
     * 宽度和高度都 wrap_content
     */
    fun wrapContent() {
        width.wrapContent()
        height.wrapContent()
    }

}
