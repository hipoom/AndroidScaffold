@file:Suppress("SpellCheckingInspection", "unused")

package com.hipoom.scaffold.core.style

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * @author ZhengHaiPeng
 * @since 2021/9/9 01:18
 */
open class ActivityStyle {

    /**
     * 标题栏
     */
    private val toolbarStyle = ToolbarStyle()
    fun toolbar(scope: ToolbarStyle.()->Unit) {
        scope(toolbarStyle)
    }

    /**
     * 内容区域
     */
    private val bodyStyle = BodyStyle()
    fun body(scope: BodyStyle.()->Unit) {
        scope(bodyStyle)
    }

    /**
     * 状态栏
     */
    private val statusBarStyle = StatusBarStyle()
    fun statusBar(scope: StatusBarStyle.()->Unit) {
        scope(statusBarStyle)
    }

    fun apply(activity: AppCompatActivity, toolbar: Toolbar) {
        toolbarStyle.apply(activity, toolbar)
        bodyStyle.apply(activity)
        statusBarStyle.apply(activity)
    }

}