@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.core.drawable

/**
 * 用于创建 Drawable 的圆角半径。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/8 12:03 AM
 */
data class Radius(var topLeft     : Float? = null,
                  var topRight    : Float? = null,
                  var bottomRight : Float? = null,
                  var bottomLeft  : Float? = null,
                  var top         : Float? = null,
                  var bottom      : Float? = null,
                  var left        : Float? = null,
                  var right       : Float? = null) {

    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    fun createCornerRadii(def: Float): FloatArray {
        // 左上角
        val tl = topLeft ?: top ?: left ?: def
        // 右上角
        val tr = topRight ?: top ?: right ?: def
        // 右下角
        val br = bottomRight ?: bottom ?: right ?: def
        // 左下角
        val bl = bottomLeft ?: bottom ?: left ?: def

        return floatArrayOf(tl, tl, tr, tr, br, br, bl, bl)
    }

}