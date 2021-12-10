@file:Suppress("SpellCheckingInspection", "MemberVisibilityCanBePrivate")

package com.hipoom.scaffold.core.drawable

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.annotation.Px

/**
 * Drawable 的构建器。
 *
 * 目前只支持 圆角、边框、实心颜色 的设置。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/8 12:06 AM
 */
class DrawableBuilder {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    /**
     * 实心颜色
     */
    var color: Int = Color.TRANSPARENT

    /**
     * 统一圆角。
     * 四个角都取这个值。
     */
    @Px
    var radius: Int = 0

    /**
     * 边框信息
     */
    var stroke: Stroke? = null

    /**
     * 复杂圆角。
     * 单独设置某一个角，或者某一个边的角。
     */
    internal var radiusBox: Radius? = null



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 边框
     */
    fun stroke(block: Stroke.()->Unit) {
        val stroke = Stroke()
        block(stroke)
        this.stroke = stroke
    }

    /**
     * 复杂圆角
     */
    fun radius(initializer: Radius.()->Unit) {
        val radiusBox = Radius()
        initializer(radiusBox)
        this.radiusBox = radiusBox
    }

    /**
     * 生成 Drawable 对象。
     */
    fun create(): GradientDrawable {
        // 颜色
        val drawable = GradientDrawable()
        drawable.setColor(color)

        // 圆角
        drawable.cornerRadii = createCornerRadii()

        // 如果有边框，加边框
        stroke?.let { drawable.setStroke(it.width, it.color) }

        return drawable
    }



    /* ======================================================= */
    /* Private Methods                                         */
    /* ======================================================= */

    /**
     * 创建圆角的贝塞尔顶点。
     */
    internal fun createCornerRadii(): FloatArray? {
        val radiusBox = radiusBox

        // 没有设置统一圆角  也没有 设置单独某个角的圆角   ->  null
        if (radius == 0 && radiusBox == null) {
            return null
        }

        val r = radius.toFloat()

        // 如果没有特别设置某一个角的圆角 -> 全部设为统一的圆角
        if (radiusBox == null) {
            return floatArrayOf(r, r, r, r, r, r, r, r)
        }

        // 如果设置了特定的圆角
        return radiusBox.createCornerRadii(r)
    }

}
