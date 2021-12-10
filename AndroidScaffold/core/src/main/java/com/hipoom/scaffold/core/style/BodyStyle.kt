@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.core.style

import android.app.Activity
import android.view.View

/**
 * @author ZhengHaiPeng
 * @since 2021/9/9 01:14
 */
class BodyStyle(
    var backgroundColor: Int? = null
) {
    fun apply(activity: Activity) {
        backgroundColor?.let {
            val view = activity.findViewById<View>(android.R.id.content)
            view.setBackgroundColor(it)
        }
    }
}