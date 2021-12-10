@file:Suppress("SpellCheckingInspection", "unused", "ConvertSecondaryConstructorToPrimary",
    "MemberVisibilityCanBePrivate"
)

package com.hipoom.scaffold.core.style.widgets

import android.content.Context
import android.widget.Button
import com.hipoom.scaffold.core.style.BackgroundStyle
import com.hipoom.scaffold.core.style.MarginStyle
import com.hipoom.scaffold.core.style.TextStyle

/**
 * 按钮的风格。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/7 23:23
 */
open class ButtonStyle : TextViewStyle {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    /**
     * 点击事件。
     */
    internal var onClick: (() -> Unit)? = null



    /* ======================================================= */
    /* Constructors                                            */
    /* ======================================================= */

    constructor(
        title      : TextStyle? = null,
        elevation  : Int? = null,
        margin     : MarginStyle? = null,
        background : BackgroundStyle? = null,
        onClick    : (()->Unit)? = null
    ) : super(title, elevation, margin, background) {
        this.onClick = onClick
    }



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    override fun build(context: Context): Button {
        val button = Button(context)
        apply(button)
        return button
    }



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    open fun <V: Button> apply(button: V) {
        super.apply(button)
        onClick?.let { button.setOnClickListener { it() } }
    }

    fun onClick(block: ()->Unit) {
        this.onClick = block
    }

    companion object
}