@file:Suppress("SpellCheckingInspection", "unused")

package me.haipeng.scaffold.core.style.widgets

import android.graphics.Color
import android.widget.TextView
import androidx.annotation.Px
import me.haipeng.scaffold.core.style.*

/**
 * 按钮的风格。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/7 23:23
 */
open class TextViewStyle (

    /**
     * 文本风格
     */
    var title: TextStyle? = null,

    /**
     * Z轴高度
     */
    @Px
    elevation: Int? = null,

    /**
     * 背景风格
     */
    background: BackgroundStyle? = null

): ViewStyle(elevation, background) {

    companion object {var default = roundedRect}

    /**
     * 应用到控件上。
     */
    open fun <V: TextView> apply(view: V): V {
        // 应用 background 和 elevation
        super.apply(view)

        val self = this
        // 如果没有指定文字颜色，设置默认的文字颜色
        if (this.background?.color != null && this.title?.color == null) {
            if (this.title == null) {
                this.title = TextStyle()
            }
            this.title!!.color = background?.color?.normalTextColor
        }

        self.title?.apply(view)
        return view
    }

    fun title(builder: TextStyle.()->Unit) {
        this.title = this.title ?: TextStyle()
        builder(this.title!!)
    }
}


/* ======================================================= */
/* Extensions Fields                                       */
/* ======================================================= */

/** 圆角矩形 */
val TextViewStyle.Companion.roundedRect: TextViewStyle
    get() {
        val colors = ColorStyle.default
        return TextViewStyle(
            background = BackgroundStyle(
                ripple = RippleStyle(enable = true),
                color  = colors.primary,
                stroke = StrokeStyle(
                    color = Color.TRANSPARENT
                ),
                radius = 12
            ),
            title = TextStyle(
                color = TextColorStyle.white.normal,
            )
        )
    }


fun <V: TextView> V.withDefaultStyle(): V {
    TextViewStyle.default.apply(this)
    return this
}