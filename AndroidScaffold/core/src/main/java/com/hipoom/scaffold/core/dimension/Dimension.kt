package com.hipoom.scaffold.core.dimension

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.Px


@Px
fun Context.dip(dp: Float): Int {
    return if (Build.VERSION.SDK_INT >= 30) {
        val metrics = DisplayMetrics()
        this.display?.getRealMetrics(metrics)
        (metrics.density * dp).toInt()
    } else {
        (resources.displayMetrics.density * dp).toInt()
    }
}

fun View.dip(dp: Float) = context.dip(dp)

