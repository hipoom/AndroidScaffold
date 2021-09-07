@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.core.dimension

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.Px


@Px
fun Context.dip(dp: Float): Int {
    val metrics = DisplayMetrics()
    this.display?.getRealMetrics(metrics)
    return (metrics.density * dp).toInt()
}

fun View.dip(dp: Float) = context.dip(dp)

