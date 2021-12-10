package com.hipoom.scaffold.core.style

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Px

/**
 * @author ZhengHaiPeng
 * @since 2021/9/14 21:34
 */
class StrokeStyle(
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