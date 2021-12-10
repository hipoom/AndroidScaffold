@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.core.drawable

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import androidx.annotation.ColorInt


/**
 * 这个方法用于方便地创建一个 Drawable 对象，减少繁杂的 shape.xml 文件。
 * 使用示例：
 * ```kotlin
 * val background = drawable {
 *     radius = 5.dip
 *     color  = Color.BLUE
 * }
 * ```
 */
fun drawable(initializer: DrawableBuilder.()->Unit): GradientDrawable {
    val temp = DrawableBuilder()
    initializer.invoke(temp)
    return temp.create()
}

/**
 * 创建一个点击后有水波纹的 Drawable。
 *
 * @param color 水波纹的颜色
 * @param radii 圆角半径
 */
fun Drawable.createRippleDrawable(@ColorInt color: Int = Color.parseColor("#20000000"),
                                  radii: FloatArray? = null): RippleDrawable {
    // 暗色
    val status = arrayOf(intArrayOf())
    val colors = intArrayOf(color)
    val colorStateList = ColorStateList(status, colors)

    val shape = RoundRectShape(radii, null, null)

    val mask = ShapeDrawable()
    mask.shape = shape
    mask.paint.color = Color.BLACK
    mask.paint.style = Paint.Style.FILL

    return RippleDrawable(colorStateList, this, mask)
}

fun Drawable.setColorFilterCompat(@ColorInt color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_ATOP) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    }
    else {
        this.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}