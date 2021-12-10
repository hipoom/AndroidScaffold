@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.core.masonry.constraint.side

import android.view.View
import com.hipoom.scaffold.core.masonry.side.SIDE_BOTTOM
import com.hipoom.scaffold.core.masonry.side.SIDE_LEFT
import com.hipoom.scaffold.core.masonry.side.SIDE_RIGHT
import com.hipoom.scaffold.core.masonry.side.SIDE_TOP

/*
继承关系图：

                                        +-------------------+
                                        |                   |
                                        | OneSideConstraint |
                                        |                   |
                                        +---------+---------+
                                                  |
                          +-----------------------+------------------------+
                          |                                                |
                          v                                                v
             +------------+-------------+                     +------------+-----------+
             |                          |                     |                        |
             | HorizontalSideConstraint |                     | VerticalSideConstraint |
             |                          |                     |                        |
             +------------+-------------+                     +------------+-----------+
                          |                                                |
             +------------+-----------+                       +------------+-----------+
             |                        |                       |                        |
             v                        v                       v                        v
  +----------+----------+   +---------+---------+    +--------+----------+  +----------+-----------+
  |                     |   |                   |    |                   |  |                      |
  | StartSideConstraint |   | EndSideConstraint |    | TopSideConstraint |  | BottomSideConstraint |
  |                     |   |                   |    |                   |  |                      |
  +---------------------+   +-------------------+    +-------------------+  +----------------------+
 */

/**
 * 某一条边上的约束。
 *
 * @author ZhengHaiPeng
 * @since  2021年09月08日02:55:40
 */
open class OneSideConstraint(
    val owner: View,
    val ownerSide: Int,
    var target: View? = null,
    var targetSide: Int? = null,
    var margin: Int = 0
)


/** 左边 或者 右边 */
open class HorizontalSideConstraint(
    owner: View,
    ownerSide: Int,
    target: View? = null,
    targetSide: Int? = null,
    margin: Int = 0
): OneSideConstraint(owner, ownerSide, target, targetSide, margin)

/** 上边 或者 下边 */
open class VerticalSideConstraint(
    owner: View,
    ownerSide: Int,
    target: View? = null,
    targetSide: Int? = null,
    margin: Int = 0
): OneSideConstraint(owner, ownerSide, target, targetSide, margin)





class StartSideConstraint(
    owner: View,
    target: View? = null,
    targetSide: Int? = null,
    margin: Int = 0
): HorizontalSideConstraint(owner, SIDE_LEFT, target, targetSide, margin)


class EndSideConstraint(
    owner: View,
    target: View? = null,
    targetSide: Int? = null,
    margin: Int = 0
): HorizontalSideConstraint(owner, SIDE_RIGHT, target, targetSide, margin)

typealias LeftSideConstraint = StartSideConstraint
typealias RightSideConstraint = EndSideConstraint







class TopSideConstraint(
    owner: View,
    target: View? = null,
    targetSide: Int? = null,
    margin: Int = 0
): VerticalSideConstraint(owner, SIDE_TOP, target, targetSide, margin)

class BottomSideConstraint(
    owner: View,
    target: View? = null,
    targetSide: Int? = null,
    margin: Int = 0
): VerticalSideConstraint(owner, SIDE_BOTTOM, target, targetSide, margin)