@file:Suppress("SpellCheckingInspection")
package com.hipoom.scaffold.core.style

import android.database.Observable
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.Px
import androidx.lifecycle.LiveData
import java.util.*

/**
 * @author ZhengHaiPeng
 * @since 2021/9/14 16:22
 */
open class TextStyle(
    var text  : CharSequence? = null,
    var color : Int? = null,
    @Px
    var size:  Int? = null,
    var dynamicText: LiveData<CharSequence>? = null
) {
    companion object

    fun apply(view: TextView) {
        text?.let { view.text = it }
        color?.let { view.setTextColor(it) }
        size?.let { view.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat()) }
    }
}