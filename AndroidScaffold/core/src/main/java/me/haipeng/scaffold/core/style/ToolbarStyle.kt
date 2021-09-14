@file:Suppress("SpellCheckingInspection", "MemberVisibilityCanBePrivate")

package me.haipeng.scaffold.core.style

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.core.content.ContextCompat
import com.google.android.material.R
import me.haipeng.scaffold.core.drawable.setColorFilterCompat


/**
 * Toolbar 的配置。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/9 01:12
 */
class ToolbarStyle {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    var title : TextStyle? = null
    var icon : IconStyle? = null
    var backgroundColor : Int? = null



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 设置 Toolbar 左侧的图标信息。
     */
    fun icon(scope: IconStyle.()->Unit) {
        if (this.icon == null) {
            this.icon = IconStyle().apply(scope)
        } else {
            this.icon!!.apply(scope)
        }
    }

    /**
     * 设置标题
     */
    fun title(builder: TextStyle.() -> Unit) {
        this.title = this.title ?: TextStyle()
        builder(this.title!!)
    }

    fun apply(activity: AppCompatActivity, toolbar: Toolbar) {
        // Toolbar 的背景色
        backgroundColor = backgroundColor ?: ColorStyle.default.primary
        toolbar.setBackgroundColor(backgroundColor!!)

        // 背景色是否为亮色
        val isLightBackground = backgroundColor!!.isLightColor()

        // 标题文字
        toolbar.title = title?.text

        // 标题颜色
        title = (title ?: TextStyle()).apply {
            color = color ?: if (isLightBackground) TextColorStyle.black.normal else TextColorStyle.white.normal
        }
        toolbar.setTitleTextColor(title!!.color!!)

        // 如果需要 icon 信息
        if (icon?.enable == true) {
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                activity.onBackPressed()
            }

            ContextCompat.getDrawable(activity, R.drawable.abc_ic_ab_back_material)?.apply {
                setColorFilterCompat(title?.color!!)
                activity.supportActionBar?.setHomeAsUpIndicator(this)
            }
        }
    }



    /* ======================================================= */
    /* Inner Class                                             */
    /* ======================================================= */

    class IconStyle (
        var enable: Boolean? = null,
        var color: Int? = null
    )
}

