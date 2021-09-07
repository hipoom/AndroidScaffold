@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.core.style

import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import me.haipeng.scaffold.core.dimension.dip
import me.haipeng.scaffold.core.drawable.background

/**
 * 按钮的风格。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/7 23:23
 */
data class ButtonStyle(

    /**
     * 背景颜色
     */
    @ColorInt
    val backgroundColor: Int,

    /**
     * 边框颜色
     */
    @ColorInt
    val strokeColor: Int,

    /**
     * 文字颜色
     */
    @ColorInt
    val textColor: Int,

    /**
     * 圆角半径
     */
    @Dimension(unit = Dimension.DP)
    val radius: Float

) {companion object {var default = roundedRect} }


/* ======================================================= */
/* Extensions Fields                                       */
/* ======================================================= */

/** 圆角矩形 */
val ButtonStyle.Companion.roundedRect: ButtonStyle
    get() {
        val colors = ColorStyle.default
        return ButtonStyle(
            backgroundColor = colors.primary,
            strokeColor = Color.TRANSPARENT,
            textColor = Color.WHITE,
            radius = 4F
        )
    }

fun <V: TextView> ButtonStyle.apply(view: V): V {
    val self = this
    view.background {
        color = self.backgroundColor
        radius = view.dip(self.radius)
        stroke {
            color = self.strokeColor
            width = view.dip(0.5F)
        }
    }
    view.setTextColor(self.textColor)
    return view
}

fun <V: TextView> V.withDefaultStyle(): V {
    ButtonStyle.default.apply(this)
    return this
}