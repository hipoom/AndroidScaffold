@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.core.drawable

import androidx.annotation.Px

/**
 * 用于创建 Drawable 的圆角半径。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/8 12:03 AM
 */
class Radius(@Px var topLeft     : Int? = null,
             @Px var topRight    : Int? = null,
             @Px var bottomRight : Int? = null,
             @Px var bottomLeft  : Int? = null,
             @Px var top         : Int? = null,
             @Px var bottom      : Int? = null,
             @Px var left        : Int? = null,
             @Px var right       : Int? = null) {

    /* ======================================================= */
    /* Constructors                                            */
    /* ======================================================= */

    /**
     * 创建一个所有角都是 [allCorner] 的半径描述对象。
     */
    constructor(@Px allCorner: Int?) : this() {
        this.topLeft     = allCorner
        this.topRight    = allCorner
        this.bottomRight = allCorner
        this.bottomLeft  = allCorner
        this.top         = allCorner
        this.bottom      = allCorner
        this.left        = allCorner
        this.right       = allCorner
    }



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 创建矩形的四个圆角基于二次贝塞尔曲线的描述数组。
     */
    fun createCornerRadii(def: Float): FloatArray {
        // 左上角
        val tl = topLeft ?: top ?: left ?: def
        // 右上角
        val tr = topRight ?: top ?: right ?: def
        // 右下角
        val br = bottomRight ?: bottom ?: right ?: def
        // 左下角
        val bl = bottomLeft ?: bottom ?: left ?: def

        return floatArrayOf(
            tl.toFloat(),
            tl.toFloat(),
            tr.toFloat(),
            tr.toFloat(),
            br.toFloat(),
            br.toFloat(),
            bl.toFloat(),
            bl.toFloat()
        )
    }

}