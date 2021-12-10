@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.core.style

import android.graphics.Color
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import com.hipoom.scaffold.core.dimension.dip
import com.hipoom.scaffold.core.drawable.background

/**
 * 按钮的风格。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/7 23:23
 */
open class ButtonStyle (

    /**
     * 背景颜色
     */
    @ColorInt
    var backgroundColor: Int? = null,

    /**
     * 边框颜色
     */
    @ColorInt
    var strokeColor: Int? = null,

    /**
     * 文字颜色
     */
    @ColorInt
    var textColor: Int? = null,

    /**
     * 圆角半径
     */
    @Dimension(unit = Dimension.DP)
    var radius: Float? = null

) { companion object {var default = roundedRect} }


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
        color = self.backgroundColor ?: color
        radius = view.dip(self.radius ?: 0F)
        stroke {
            color = self.strokeColor ?: color
            width = view.dip(0.5F)
        }
    }
    self.textColor?.let { view.setTextColor(it) }
    return view
}

fun <V: TextView> V.withDefaultStyle(): V {
    ButtonStyle.default.apply(this)
    return this
}