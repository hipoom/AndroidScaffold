@file:Suppress("unused")

package me.haipeng.scaffold.core.masonry
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import me.haipeng.scaffold.core.masonry.constraint.side.OneSideConstraint
import me.haipeng.scaffold.core.masonry.side.SIDE_BOTTOM
import me.haipeng.scaffold.core.masonry.side.SIDE_LEFT
import me.haipeng.scaffold.core.masonry.side.SIDE_RIGHT
import me.haipeng.scaffold.core.masonry.side.SIDE_TOP

/**
 * @author ZhengHaiPeng
 * @since 2019-12-02 20:43
 *
 */


/* ======================================================= */
/* Public Methods                                          */
/* ======================================================= */

infix fun View.aligns(block: ConstraintMake.()->Unit) {
    val make = ConstraintMake(this)
    block.invoke(make)
    setupConstraint(make)
}



/* ======================================================= */
/* Private Methods                                         */
/* ======================================================= */

private fun setupConstraint(make: ConstraintMake) {

    // 判断是否已经添加到父布局中了
    val parent = make.owner.parent as? ConstraintLayout ?:
    throw IllegalStateException("View 需要添加到父布局中才能使用 Masonry！")

    var id = make.owner.id
    if (id == View.NO_ID) {
        id = View.generateViewId()
        make.owner.id = id
    }

    // 给每一个子 View 都设置一个 ID
    parent.setEveryChildViewAnID()

    val cs = ConstraintSet()

    // 从当前父布局中复制一份已有的约束条件
    cs.clone(parent)

    // 清除当前 View 的所有约束
    cs.clear(make.owner.id)

    fun setupSide(side: Int, constraint: OneSideConstraint) {
        val target = constraint.target ?: return

        val targetID =
            when {
                // 如果是爹，取 「PARENT_ID」
                target == parent -> ConstraintSet.PARENT_ID
                // 如果没有 id， 设置一个ID
                target.id == View.NO_ID -> {
                    target.id = View.generateViewId()
                    target.id
                }
                // 不是爹 且 有 ID，取他的 id
                else -> target.id
            }

        cs.connect(id, side, targetID, constraint.targetSide!!, constraint.margin)
    }

    // 上下左右四条边
    setupSide(SIDE_LEFT, make.left)
    setupSide(SIDE_RIGHT, make.right)
    setupSide(SIDE_TOP, make.top)
    setupSide(SIDE_BOTTOM, make.bottom)

    // 尺寸
    cs.constrainWidth(id, make.width.value)
    cs.constrainHeight(id, make.height.value)

    cs.applyTo(parent)
}


/**
 * 给所有子 View 一个ID
 */
private fun ConstraintLayout.setEveryChildViewAnID() {
    for (i in 0 until childCount) {
        val tempView = getChildAt(i)
        if (tempView.id > 0) {
            continue
        }

        tempView.id = View.generateViewId()
    }
}