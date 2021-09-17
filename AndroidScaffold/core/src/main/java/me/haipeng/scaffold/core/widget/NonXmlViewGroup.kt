package me.haipeng.scaffold.core.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 不需要 xml 布局文件的布局。
 *
 * @author ZhengHaiPeng
 * @since 2021/9/15 15:50
 */
open class NonXmlViewGroup: ConstraintLayout {

    /* ======================================================= */
    /* Constructors                                            */
    /* ======================================================= */

    constructor(context: Context): super(context) {
        onInit()
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        onInit()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        onInit()
    }



    /* ======================================================= */
    /* Private Methods                                         */
    /* ======================================================= */

    protected open fun onInit() {

    }
}