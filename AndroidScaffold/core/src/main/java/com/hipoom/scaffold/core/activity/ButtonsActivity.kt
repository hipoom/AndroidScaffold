@file:Suppress("SpellCheckingInspection", "unused", "MemberVisibilityCanBePrivate")
package com.hipoom.scaffold.core.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hipoom.scaffold.core.R
import com.hipoom.scaffold.core.style.ActivityStyle
import com.hipoom.scaffold.core.style.widgets.VerticalStyle
import java.util.concurrent.atomic.AtomicInteger

/**
 * 带少量按钮的 Activity。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/9 01:12
 */
class ButtonsActivity : AppCompatActivity() {

    companion object {

        fun declare(scope: Style.() -> Unit): Style {
            val style = Style()
            scope(style)
            return style
        }
    }


    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    lateinit var toolbar: Toolbar

    lateinit var scrollView: ScrollView



    /* ======================================================= */
    /* Override/Implements Methods                             */
    /* ======================================================= */

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaterialComponents_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scaffold_activity_only_scroll_view)

        toolbar = findViewById(R.id.toolbar)
        scrollView = findViewById(R.id.scrollView)

        // 按照 Style 初始化界面
        val index = intent.getIntExtra(INTENT_KEY_STYLE_INDEX, -1)
        val style = styles[index] ?: return
        style.apply(this)
    }



    /* ======================================================= */
    /* Inner Class                                             */
    /* ======================================================= */

    class Style : ActivityStyle() {

        internal val body = VerticalStyle()

        fun body(builder: VerticalStyle.()->Unit) {
            builder(body)
        }

        fun show(activity: Activity) {
            val intent = Intent(activity, ButtonsActivity::class.java)
            val index = counter.getAndIncrement()
            styles[index] = this
            intent.putExtra(INTENT_KEY_STYLE_INDEX, index)
            activity.startActivity(intent)
        }

        fun apply(activity: ButtonsActivity) {
            super.apply(activity, activity.toolbar)
            val content = body.build(activity)
            activity.scrollView.addView(content, MATCH_PARENT, WRAP_CONTENT)
        }

    }
}

/**
 * 启动 Activity 时用于传递参数。
 */
private const val INTENT_KEY_STYLE_INDEX = "INTENT_KEY_STYLE_INDEX"

/**
 * 计数器，用于传递 intent 数据时用。
 * 用作 [styles] 的 Key。
 */
private val counter = AtomicInteger()

/**
 * 用于启动 Activity 时保存要传递数据。
 */
private val styles = HashMap<Int, ButtonsActivity.Style>()