@file:Suppress("unused")

package me.haipeng.scaffold.core.style

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import me.haipeng.scaffold.core.drawable.DrawableBuilder
import me.haipeng.scaffold.core.drawable.Radius
import me.haipeng.scaffold.core.drawable.Stroke
import me.haipeng.scaffold.core.drawable.background

/**
 * 背景风格。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/14 21:32
 */
class BackgroundStyle(
    /**
     * 水波纹效果
     */
    var ripple: RippleStyle = RippleStyle(true),

    /**
     * 边框效果
     */
    var stroke: StrokeStyle? = null,

    /**
     * 背景颜色
     */
    @ColorInt
    var color: Int? = null,

    /**
     * 圆角
     */
    @Px
    var radius: Int? = null,
    var radiusBox: Radius? = null

) {

    /**
     * 构建圆角信息。
     */
    fun radius(builder: Radius.()->Unit) {
        this.radiusBox = this.radiusBox ?: Radius(radius)
        builder(this.radiusBox!!)
    }

    /**
     * 边框
     */
    fun stroke(builder: StrokeStyle.()->Unit) {
        this.stroke = this.stroke ?: StrokeStyle()
        builder(this.stroke!!)
    }

    /**
     * 应用到 [view] 上。
     */
    fun apply(view: View) {
        val needRipple = ripple.enable == true
        val rippleColor = ripple.color

        val self = this
        val block: DrawableBuilder.()->Unit = {
            this.color = self.color ?: Color.TRANSPARENT
            this.radius = self.radius ?: 0
            this.radiusBox = self.radiusBox
            self.stroke?.let { this.stroke = Stroke(width = it.width, color = it.color) }
        }
        view.background(needRipple = needRipple, rippleColor = rippleColor, block)
    }

}