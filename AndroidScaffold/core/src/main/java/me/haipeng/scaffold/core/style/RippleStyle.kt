package me.haipeng.scaffold.core.style

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * 水波纹效果。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/14 21:35
 */
class RippleStyle(
    /**
     * 是否启用水波纹
     */
    var enable: Boolean? = null,

    /**
     * 水波纹的颜色。
     */
    @ColorInt
    var color : Int = Color.parseColor("#20000000")
)