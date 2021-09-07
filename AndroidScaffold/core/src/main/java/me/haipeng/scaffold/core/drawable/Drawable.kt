@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.core.drawable

import android.graphics.drawable.GradientDrawable

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
