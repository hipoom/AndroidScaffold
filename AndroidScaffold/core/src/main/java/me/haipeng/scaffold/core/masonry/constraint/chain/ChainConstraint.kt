@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.core.masonry.constraint.chain

import android.view.View
import me.haipeng.scaffold.core.masonry.ConstraintMake

/**
 * 锁链约束。
 *
 * @author ZhengHaiPeng
 * @since 2020/8/5 11:22 AM
 */
class ChainConstraint(val make: ConstraintMake) {

    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 把当前 View 当做锁链的头结点，创建一条锁链。
     */
    infix fun horizontal(views: List<View>) {

        var pre = make.owner

        views.forEach {
        }
    }

}