@file:Suppress("ConvertSecondaryConstructorToPrimary", "MemberVisibilityCanBePrivate")

package com.hipoom.scaffold.core.style.widgets

import android.content.Context
import android.view.View
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import com.hipoom.scaffold.core.style.BackgroundStyle
import com.hipoom.scaffold.core.style.MarginStyle

/**
 * @author ZhengHaiPeng
 * @since 2021/9/14 21:31
 */
open class ViewStyle {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    @Dimension(unit = DP)
    var elevation: Int? = null

    var background: BackgroundStyle? = null

    internal var marginBox: MarginStyle? = null

    @Dimension(unit = DP)
    var margin: Float? = null
        set(value) {
            field = value
            marginBox = marginBox ?: MarginStyle()
            marginBox!!.margin = value
        }



    /* ======================================================= */
    /* Constructors                                            */
    /* ======================================================= */

    constructor(elevation: Int? = null, background: BackgroundStyle? = null, marginBox: MarginStyle? = null) {
        this.elevation = elevation
        this.background = background
        this.marginBox = marginBox
    }



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    fun background(builder: BackgroundStyle.()->Unit) {
        this.background = this.background ?: BackgroundStyle()
        builder(this.background!!)
    }

    fun margin(builder: MarginStyle.()->Unit) {
        this.marginBox = this.marginBox ?: MarginStyle()
        builder(this.marginBox!!)
    }

    open fun apply(view: View) {
        // 应用背景
        this.background?.apply(view)
        // 应用高度
        elevation?.let { view.elevation = it.toFloat() }
        // 应用 margins
        marginBox?.apply(view)
    }

    open fun build(context: Context): View {
        val view = View(context)
        apply(view)
        return view
    }

}