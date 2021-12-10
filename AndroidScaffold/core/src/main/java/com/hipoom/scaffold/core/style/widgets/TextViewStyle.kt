@file:Suppress(
    "SpellCheckingInspection",
    "unused",
    "ConvertSecondaryConstructorToPrimary",
    "MemberVisibilityCanBePrivate"
)

package com.hipoom.scaffold.core.style.widgets

import android.content.Context
import android.widget.TextView
import com.hipoom.scaffold.core.style.BackgroundStyle
import com.hipoom.scaffold.core.style.*

/**
 * 按钮的风格。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/7 23:23
 */
open class TextViewStyle: ViewStyle {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */


    /**
     * 文本风格
     */
    var title: TextStyle? = null



    /* ======================================================= */
    /* Constructors                                            */
    /* ======================================================= */

    constructor(
        title      : TextStyle? = null,
        elevation  : Int? = null,
        margin     : MarginStyle? = null,
        background : BackgroundStyle? = null
    ) : super(elevation, background, margin) {
        this.title = title
    }



    /* ======================================================= */
    /* Override/Implements Methods                             */
    /* ======================================================= */

    /**
     * 创建出一个 TextView 对象。
     */
    override fun build(context: Context): TextView {
        val view = TextView(context)
        apply(view)
        return view
    }



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    open fun label(builder: TextStyle.()->Unit) {
        this.title = this.title ?: TextStyle()
        builder(this.title!!)
    }

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

    companion object
}