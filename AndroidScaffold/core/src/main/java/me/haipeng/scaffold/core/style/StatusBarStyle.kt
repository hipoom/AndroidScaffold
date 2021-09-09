package me.haipeng.scaffold.core.style

import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager


/**
 * 状态栏风格。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/9 01:16
 */
class StatusBarStyle(
    var color: Int? = null
) {
    fun apply(activity: AppCompatActivity) {
        color?.let {
            val window: Window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = it
        }
    }
}