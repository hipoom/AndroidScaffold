package me.haipeng.scaffold.core.style

import android.view.View
import androidx.annotation.Px

/**
 * @author ZhengHaiPeng
 * @since 2021/9/14 21:31
 */
open class ViewStyle(
    @Px
    var elevation: Int? = null,

    var background: BackgroundStyle? = null
) {

    fun background(builder: BackgroundStyle.()->Unit) {
        this.background = this.background ?: BackgroundStyle()
        builder(this.background!!)
    }

    open fun apply(view: View) {
        // 应用背景
        this.background?.apply(view)
        // 应用高度
        elevation?.let { view.elevation = it.toFloat() }
    }

}