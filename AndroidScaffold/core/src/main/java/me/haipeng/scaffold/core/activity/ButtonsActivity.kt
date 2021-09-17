@file:Suppress("SpellCheckingInspection", "unused", "MemberVisibilityCanBePrivate")
package me.haipeng.scaffold.core.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import me.haipeng.scaffold.core.R
import me.haipeng.scaffold.core.drawable.background
import me.haipeng.scaffold.core.style.ActivityStyle
import me.haipeng.scaffold.core.style.ColorStyle
import me.haipeng.scaffold.core.style.jianshu
import me.haipeng.scaffold.core.style.widgets.VerticalStyle
import java.util.concurrent.atomic.AtomicInteger

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
        setContentView(R.layout.scaffold_activity_buttons)

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

//    class Style: ActivityStyle() {
//
//        /**
//         *
//         * Usage:
//         *
//         *  VerticalWidgetsActivity.build {
//         *      button {
//         *      }
//         *
//         *      text {
//         *      }
//         *
//         *      button {
//         *      }
//         *  }.show(this)
//         */
//
//        /**
//         * 控件信息。
//         */
//        private val widgets = LinkedList<WidgetBuilder>()
//
//        /**
//         * 用法示例：
//         * buttons {
//         *     button {
//         *         title = "Your Button's Title"
//         *         onClick {
//         *             // Handle Your Button's Tap Event
//         *         }
//         *     }
//         * }
//         */
//        fun button(buttonsBuilder: ButtonInfo.()->Unit) {
//            val builder = ButtonsBuilder()
//            widgets.add(builder)
//            buttonsBuilder(builder)
//        }
//
//        fun apply(activity: ButtonsActivity) {
//            super.apply(activity, activity.toolbar)
//
//            // 逐个添加按钮到容器中
//            widgets.forEach {
//
//            }
//            buttonBuilder.buttonsInfo.forEach {
//                val button = Button(activity)
//                button.isAllCaps = false
//                activity.buttonsContainer.addView(button, MATCH_PARENT, WRAP_CONTENT)
//                val lp = button.layoutParams as ViewGroup.MarginLayoutParams
//                lp.topMargin = button.dip(buttonBuilder.margin)
//                button.layoutParams = lp
//                it.apply(button)
//            }
//        }
//
//    }

//    class Config(val style: Style) {
//        fun show(activity: Activity) {
//            val intent = Intent(activity, ButtonsActivity::class.java)
//            val index = counter.getAndIncrement()
//            configs[index] = this
//            intent.putExtra(INTENT_KEY_STYLE_INDEX, index)
//            activity.startActivity(intent)
//        }
//    }

//    class ButtonInfo: TextViewStyle() {
//        var onClick: (()->Unit)? = null
//        private var onAppliedCallback: (TextView.()->Unit)? = null
//
//        fun onClick(block: ()->Unit) {
//            this.onClick = block
//        }
//
//        override fun apply(view: View) {
//            super.apply(view)
//            onAppliedCallback?.let { it(view as TextView) }
//        }
//
//        fun onApplied(block: TextView.()->Unit) {
//            onAppliedCallback = block
//        }
//
//        override fun <V : TextView> apply(view: V): V {
//            if (elevation == null) {
//                this.elevation = view.dip(4F)
//            }
//            super.apply(view)
//            onClick?.let { view.setOnClickListener { it() } }
//            return view
//        }
//    }

//    class ButtonsBuilder {
//
//        internal val buttonsInfo = LinkedList<ButtonInfo>()
//
//        companion object {
//            const val LAYOUT_STYLE_LINEAR = 0
//            const val LAYOUT_STYLE_GRID   = 1
//        }
//
//        /**
//         * 布局风格。
//         * 0: 线性布局
//         * 1: 网格布局
//         *
//         * @see LAYOUT_STYLE_LINEAR
//         * @see LAYOUT_STYLE_GRID
//         */
//        var layoutStyle: Int = LAYOUT_STYLE_LINEAR
//
//        /**
//         * 按钮之间的间隔。
//         * 这个值会被设置到每一个按钮的 marginTop 上。
//         */
//        @Dimension(unit = DP)
//        var margin: Float = 16F
//
//        fun button(info: ButtonInfo.()->Unit) {
//            val temp = ButtonInfo()
//            info(temp)
//            buttonsInfo.add(temp)
//        }
//
//        fun build() = buttonsInfo
//    }
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