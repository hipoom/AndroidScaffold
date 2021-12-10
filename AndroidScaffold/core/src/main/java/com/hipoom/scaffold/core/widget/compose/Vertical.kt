package com.hipoom.scaffold.core.widget.compose

import android.content.Context
import androidx.appcompat.widget.LinearLayoutCompat
import me.haipeng.scaffold.core.widget.NonXmlViewGroup

/**
 * 垂直布局。
 * 自从 Google 推出 Compose 之后，这就有点重复造轮子了。
 *
 * @author ZhengHaiPeng
 * @since  2021年09月15日16:09:02
 */
class Vertical(context: Context) : NonXmlViewGroup(context) {
    val container by lazy { LinearLayoutCompat(context) }
}

fun NonXmlViewGroup.vertical() {
    val view = Vertical(context)
    
}

