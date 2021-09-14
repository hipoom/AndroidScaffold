@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.core.style

import android.graphics.Color
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
open class ButtonStyle (

    /**
     * 文本风格
     */
    var title: TextStyle? = null,

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
     * 圆角半径
     */
    @Dimension(unit = Dimension.DP)
    var radius: Float? = null

) {
    companion object {var default = roundedRect}

    /**
     * 应用到控件上。
     */
    open fun <V: TextView> apply(view: V): V {
        val self = this
        view.background {
            color = self.backgroundColor ?: color
            radius = view.dip(self.radius ?: 0F)
            stroke {
                color = self.strokeColor ?: color
                width = view.dip(0.5F)
            }
        }

        // 如果没有指定文字颜色，设置默认的文字颜色
        if (this.backgroundColor != null && this.title?.color == null) {
            if (this.title == null) {
                this.title = TextStyle()
            }
            this.title!!.color = if (backgroundColor.isLightColor())
                TextColorStyle.black.normal
            else
                TextColorStyle.white.normal
        }

        self.title?.apply(view)
        return view
    }

    fun title(builder: TextStyle.()->Unit) {
        if (this.title == null) {
            this.title = TextStyle()
        }
        builder(this.title!!)
    }
}


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
            radius = 4F,
            title = TextStyle(
                color = TextColorStyle.white.normal,
            )
        )
    }


fun <V: TextView> V.withDefaultStyle(): V {
    ButtonStyle.default.apply(this)
    return this
}