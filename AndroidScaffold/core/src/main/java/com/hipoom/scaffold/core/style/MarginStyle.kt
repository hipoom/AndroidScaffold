package com.hipoom.scaffold.core.style

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import com.hipoom.scaffold.core.dimension.dip

/**
 * @author ZhengHaiPeng
 * @since 2021/9/16 18:09
 *
 */
class MarginStyle(
    @Dimension(unit = DP) var start      : Float? = null,
    @Dimension(unit = DP) var top        : Float? = null,
    @Dimension(unit = DP) var end        : Float? = null,
    @Dimension(unit = DP) var bottom     : Float? = null,
    @Dimension(unit = DP) var vertical   : Float? = null,
    @Dimension(unit = DP) var horizontal : Float? = null,
    @Dimension(unit = DP) var margin     : Float? = null
) {

    fun apply(view: View) {
        val lp = view.layoutParams ?: ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        if (lp !is ViewGroup.MarginLayoutParams) {
            return
        }
        val topTemp    = top    ?: vertical   ?: margin
        val bottomTemp = bottom ?: vertical   ?: margin
        val startTemp  = start  ?: horizontal ?: margin
        val endTemp    = end    ?: horizontal ?: margin
        topTemp?.let { lp.topMargin = view.dip(it) }
        bottomTemp?.let { lp.bottomMargin = view.dip(it) }
        startTemp?.let { lp.marginStart = view.dip(it) }
        endTemp?.let { lp.marginEnd = view.dip(it) }
        view.layoutParams = lp
    }

}