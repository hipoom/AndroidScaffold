package com.hipoom.scaffold.core.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.hipoom.scaffold.core.R
import com.hipoom.scaffold.core.style.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.HashMap

class ButtonsActivity : AppCompatActivity() {

    companion object {
        const val INTENT_KEY_STYLE_INDEX = "INTENT_KEY_STYLE_INDEX"

        val counter = AtomicInteger()
        val configs = HashMap<Int, Config>()
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



    /* ======================================================= */
    /* Override/Implements Methods                             */
    /* ======================================================= */

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaterialComponents_Light_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scaffold_activity_buttons)

        toolbar = findViewById(R.id.toolbar)

        // 按照 Style 初始化界面
        val index = intent.getIntExtra(INTENT_KEY_STYLE_INDEX, -1)
        val config = configs[index]
        config?.style?.apply(this)
    }



    /* ======================================================= */
    /* Inner Class                                             */
    /* ======================================================= */

    class Style: ActivityStyle() {

        private val buttonsInfo = LinkedList<ButtonInfo>()

        /**
         * 用法示例：
         * buttons {
         *     button {
         *         title = "按钮"
         *         onClick {
         *             // 点击事件
         *         }
         *     }
         * }
         */
        fun buttons(buttonsBuilder: ButtonsBuilder.()->Unit) {
            val builder = ButtonsBuilder()
            buttonsBuilder(builder)
            buttonsInfo.addAll(builder.build())
        }

        fun apply(activity: ButtonsActivity) {
            super.apply(activity, activity.toolbar)

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
        var title: String? = null
        var onClick: (()->Unit)? = null
    }

    class ButtonsBuilder {

        private val buttonsInfo = LinkedList<ButtonInfo>()

        fun button(info: ButtonInfo.()->Unit) {
            val temp = ButtonInfo()
            info(temp)
            buttonsInfo.add(temp)
        }

        fun build() = buttonsInfo
    }
}