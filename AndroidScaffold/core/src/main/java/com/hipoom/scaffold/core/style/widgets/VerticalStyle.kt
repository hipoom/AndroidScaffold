@file:Suppress("unused")

package com.hipoom.scaffold.core.style.widgets

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.LinearLayoutCompat.VERTICAL
import java.util.*

/**
 * 用于声明布局的。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/16 15:15
 */
open class VerticalStyle {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    /**
     * 保存的控件列表信息。
     */
    private val widgets = LinkedList<ViewStyle>()



    /* ======================================================= */
    /* Public Methods                                          */
    /* ======================================================= */

    /**
     * 声明一个按钮。
     */
    fun button(builder: ButtonStyle.()->Unit) {
        val button = ButtonStyle()
        widgets.add(button)
        builder(button)
    }

    /**
     * 声明一段文本。
     */
    fun text(builder: TextViewStyle.()->Unit) {
        val text = TextViewStyle()
        widgets.add(text)
        builder(text)
    }

    /**
     * 根据声明，构建出布局。
     *
     * @return 构建出的布局对象， orientation 是 VERTICAL。
     */
    fun build(context: Context): LinearLayoutCompat {
        // 构建出视图
        val self = LinearLayoutCompat(context)
        self.orientation = VERTICAL

        // 添加声明的每一个子控件
        widgets.forEach { description ->
            val view = description.build(context)
            self.addView(view, MATCH_PARENT, WRAP_CONTENT)
            description.apply(view)
        }

        return self
    }

}

/**
 * Usage:
 *
 * 普适：
 * ```kotlin
 *  SomeActivity.declare {
 *      vertical {
 *          button {
 *          }
 *          text {
 *          }
 *          horizontal {
 *              button {
 *              }
 *              text {
 *              }
 *          }
 *      }
 *  }.show(this)
 * ```
 *
 * 垂直线性：
 * ```kotlin
 *  VerticalLinearActivity.declare {
 *      button {
 *      }
 *      text {
 *      }
 *      horizontal {
 *          wrapContent {
 *
 *          }
 *          fixedSize(100.dp) {
 *
 *          }
 *      }
 *  }.show(this)
 * ```
 */