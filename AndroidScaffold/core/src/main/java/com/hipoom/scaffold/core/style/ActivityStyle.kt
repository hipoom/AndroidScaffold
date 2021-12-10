@file:Suppress("SpellCheckingInspection", "unused")

package com.hipoom.scaffold.core.style

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hipoom.scaffold.core.style.widgets.ToolbarStyle

/**
 * @author ZhengHaiPeng
 * @since 2021/9/9 01:18
 */
open class ActivityStyle {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    /**
     * 标题栏
     */
    private val toolbarStyle = ToolbarStyle()
    fun toolbar(scope: ToolbarStyle.()->Unit) {
        scope(toolbarStyle)
    }

    /**
     * 状态栏
     */
    private val statusBarStyle = StatusBarStyle()
    fun statusBar(scope: StatusBarStyle.()->Unit) {
        scope(statusBarStyle)
    }



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    fun apply(activity: AppCompatActivity, toolbar: Toolbar) {
        toolbarStyle.apply(activity, toolbar)
        statusBarStyle.apply(activity)
    }

}