@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import me.haipeng.scaffold.app.databinding.ActivityMainBinding
import me.haipeng.scaffold.core.dimension.dip
import me.haipeng.scaffold.core.drawable.drawable

/**
 * 这是一个示例 app，用于展示 AndroidScaffold 的各个功能。
 *
 * @author ZhengHaiPeng
 * @since  2021年09月07日23:54:29
 */
class MainActivity : AppCompatActivity() {

    /* ======================================================= */
    /* Fields                                                  */
    /* ======================================================= */

    /** DataBinding */
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }



    /* ======================================================= */
    /* Override/Implements Methods                             */
    /* ======================================================= */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.test.background = drawable {
            radius = dip(4F)
            color = Color.BLUE
        }
    }


}