@file:Suppress("SpellCheckingInspection", "unused")

package me.haipeng.scaffold.core.style

import androidx.annotation.ColorInt


/**
 * 文字的颜色风格。
 */
class TextColorStyle(
    @ColorInt val normal    : Int,
    @ColorInt val secondary : Int,
    @ColorInt val hint      : Int,
    @ColorInt val disable   : Int
) { companion object }


/** 黑色系文字颜色 */
val TextColorStyle.Companion.black
    get() = TextColorStyle("#DE000000".color, "#89000000".color, "#42000000".color, "#42000000".color)

/** 白色系文字颜色 */
val TextColorStyle.Companion.white
    get() = TextColorStyle("#FFFFFF".color, "#B2FFFFFF".color, "#4CFFFFFF".color, "#1FFFFFFF".color)

/**
 * 获取某种背景颜色的推荐文字颜色。
 */
fun TextColorStyle.Companion.getRecommendedTextColor(@ColorInt backgroundColor: Int?): TextColorStyle {
    if (backgroundColor.isLightColor()) {
        return black
    }
    return white
}

/**
 * 获取某种背景颜色的推荐文字颜色。
 */
internal val Int.normalTextColor: Int
    get() = TextColorStyle.getRecommendedTextColor(this).normal