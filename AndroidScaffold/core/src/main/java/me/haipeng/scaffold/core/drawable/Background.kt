@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.core.drawable

import android.graphics.Color
import android.view.View

/**
 * 为 [this] 设置背景。
 *
 * @param needRipple  是否需要点击后的涟漪效果
 * @param rippleColor 涟漪颜色
 * @param initializer Drawable 构建器
 */
fun <T: View> T.background(needRipple : Boolean = true,
                           rippleColor: Int? = Color.parseColor("#20000000"),
                           initializer: DrawableBuilder.()->Unit): T {

    // 创建基本的背景
    val realInitializer = DrawableBuilder()
    initializer.invoke(realInitializer)
    val background = realInitializer.create()

    if (!needRipple) {
        this.background = background
        return this
    }

    // 圆角
    val radii = realInitializer.createCornerRadii()

    // 生成 Ripple 并设为背景
    this.background = background.createRippleDrawable(
        color = rippleColor ?: Color.parseColor("#20000000"),
        radii = radii
    )

    return this
}