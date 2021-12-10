@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.core.masonry.side

import android.view.View

/**
 * 定义 View 的某一边。
 *
 * @author ZhengHaiPeng
 * @since 2019-12-06 20:08
 */
class Side(

    @SideDef
    val type: Int,

    val owner: View
)