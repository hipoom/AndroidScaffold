@file:Suppress("SpellCheckingInspection", "unused")
package me.haipeng.scaffold.core.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.view.marginTop
import me.haipeng.scaffold.core.R
import me.haipeng.scaffold.core.style.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.HashMap

/**
 * 带少量按钮的 Activity。
 *
 * Usage:
 * ```
 * ButtonsActivity.build {
 *      toolbar {
 *          title = "Your Title"
 *          backgroundColor = ColorStyle.jianshu.primary
 *          titleColor = Color.WHITE
 *          icon {
 *              enable = true
 *          }
 *      }
 *
 *      statusBar {
 *          color = ColorStyle.jianshu.primaryDark
 *      }
 *  }.show(activity)
 * ```
 *
 * @author ZhengHaiPeng
 * @since 2021/9/9 01:12
 */
class ButtonsActivity : AppCompatActivity() {

    companion object {

        fun build(scope: Style.() -> Unit): Config {
            val style = Style()
            scope(style)
            return Config(style)
        }
    }


    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    lateinit var toolbar: Toolbar

    lateinit var buttonsContainer: LinearLayoutCompat



    /* ======================================================= */
    /* Override/Implements Methods                             */
    /* ======================================================= */

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaterialComponents_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scaffold_activity_buttons)

        toolbar = findViewById(R.id.toolbar)
        buttonsContainer = findViewById(R.id.buttonsContainer)

        // 按照 Style 初始化界面
        val index = intent.getIntExtra(INTENT_KEY_STYLE_INDEX, -1)
        val config = configs[index]
        config?.style?.apply(this)
    }



    /* ======================================================= */
    /* Inner Class                                             */
    /* ======================================================= */

    class Style: ActivityStyle() {

        private val buttonBuilder = ButtonsBuilder()

        /**
         * 用法示例：
         * buttons {
         *     button {
         *         title = "Your Button's Title"
         *         onClick {
         *             // Handle Your Button's Tap Event
         *         }
         *     }
         * }
         */
        fun buttons(buttonsBuilder: ButtonsBuilder.()->Unit) {
            buttonsBuilder(buttonBuilder)
        }

        fun apply(activity: ButtonsActivity) {
            super.apply(activity, activity.toolbar)

            // 逐个添加按钮到容器中
            buttonBuilder.buttonsInfo.forEach {
                val button = Button(activity)
                button.isAllCaps = false
                activity.buttonsContainer.addView(button, MATCH_PARENT, WRAP_CONTENT)
                buttonBuilder.margin?.let { margin ->
                    val lp = button.layoutParams as ViewGroup.MarginLayoutParams
                    lp.topMargin = margin
                    button.layoutParams = lp
                }
                it.apply(button)
            }
        }

    }

    class Config(val style: Style) {
        fun show(activity: Activity) {
            val intent = Intent(activity, ButtonsActivity::class.java)
            val index = counter.getAndIncrement()
            configs[index] = this
            intent.putExtra(INTENT_KEY_STYLE_INDEX, index)
            activity.startActivity(intent)
        }
    }

    class ButtonInfo: ButtonStyle() {
        var onClick: (()->Unit)? = null

        fun onClick(block: ()->Unit) {
            this.onClick = block
        }

        override fun <V : TextView> apply(view: V): V {
            super.apply(view)
            onClick?.let { view.setOnClickListener { it() } }
            return view
        }
    }

    class ButtonsBuilder {

        internal val buttonsInfo = LinkedList<ButtonInfo>()

        companion object {
            const val LAYOUT_STYLE_LINEAR = 0
            const val LAYOUT_STYLE_GRID   = 1
        }

        /**
         * 布局风格。
         * 0: 线性布局
         * 1: 网格布局
         *
         * @see LAYOUT_STYLE_LINEAR
         * @see LAYOUT_STYLE_GRID
         */
        var layoutStyle: Int = LAYOUT_STYLE_LINEAR

        /**
         * 按钮之间的间隔。
         * 这个值会被设置到每一个按钮的 marginTop 上。
         */
        var margin: Int? = null

        fun button(info: ButtonInfo.()->Unit) {
            val temp = ButtonInfo()
            info(temp)
            buttonsInfo.add(temp)
        }

        fun build() = buttonsInfo
    }
}

/**
 * 启动 Activity 时用于传递参数。
 */
private const val INTENT_KEY_STYLE_INDEX = "INTENT_KEY_STYLE_INDEX"

/**
 * 计数器，用于传递 intent 数据时用。
 * 用作 [configs] 的 Key。
 */
private val counter = AtomicInteger()

/**
 * 用于启动 Activity 时保存要传递数据。
 */
private val configs = HashMap<Int, ButtonsActivity.Config>()