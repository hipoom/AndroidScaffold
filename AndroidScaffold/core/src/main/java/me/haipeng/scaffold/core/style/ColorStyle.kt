@file:Suppress("unused", "SpellCheckingInspection")
package me.haipeng.scaffold.core.style

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * 颜色主题。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/8 12:59 AM
 */
class ColorStyle(

    /**
     * 主色调
     */
    @ColorInt
    val primary: Int,

    /**
     * 暗色调
     */
    @ColorInt
    val primaryDark: Int,

    /**
     * 提示色
     */
    @ColorInt
    val accent: Int
) { companion object {var default = amap } }


/* ======================================================= */
/* Extensions Fields                                       */
/* ======================================================= */

val ColorStyle.Companion.red: ColorStyle
    get() = ColorStyle("#f44336".color, "#d32f2f".color, "#ffcdd2".color)

val ColorStyle.Companion.orange: ColorStyle
    get() = ColorStyle("#FF9800".color, "#F57C00".color, "#FFE0B2".color)

val ColorStyle.Companion.blue: ColorStyle
    get() = ColorStyle("#2196f3".color, "#1976d2".color, "#82b1ff".color)

val ColorStyle.Companion.green: ColorStyle
    get() = ColorStyle("#4caf50".color, "#388e3c".color, "#e8f5e9".color)

/** 简书主题色 */
val ColorStyle.Companion.jianshu: ColorStyle
    get() = ColorStyle("#CE7B66".color, "#E96A51".color, "#EAB3A7".color)

/** 知乎主题色 */
val ColorStyle.Companion.zhihu: ColorStyle
    get() = ColorStyle("#2369F6".color, "#1950DC".color, blue.accent)

/** 高德主题色 */
val ColorStyle.Companion.amap: ColorStyle
    get() = ColorStyle("#4287FF".color, "#4365CC".color, blue.accent)


private val String.color: Int
    get() = Color.parseColor(this)