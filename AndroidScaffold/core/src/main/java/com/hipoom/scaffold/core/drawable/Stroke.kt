@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.core.drawable

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Px

/**
 * 用于创建 Drawable 的边框信息。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/8 12:05 AM
 */
data class Stroke(

    /**
     * 边框宽度
     */
    @Px
    var width: Int = 0,

    /**
     * 边框颜色
     */
    @ColorInt
    var color: Int = Color.TRANSPARENT
)