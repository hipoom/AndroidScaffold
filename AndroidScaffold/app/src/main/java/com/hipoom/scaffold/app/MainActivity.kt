@file:Suppress("SpellCheckingInspection")

package com.hipoom.scaffold.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.hipoom.scaffold.app.databinding.ActivityMainBinding
import com.hipoom.scaffold.core.activity.ButtonsActivity
import com.hipoom.scaffold.core.style.*

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

        binding.composeView.setContent {
            Sense()
        }
    }



    /* ======================================================= */
    /* Private Methods                                         */
    /* ======================================================= */

    @Composable
    private fun Sense() {
        Row {
            Text(text = "Hello Compose")
            Button(onClick = {
                ButtonsActivity.build {
                    toolbar {
                        title = "标题"
                        backgroundColor = ColorStyle.zhihu.primary
                        icon {
                            enable = true
                        }
                    }
                    statusBar {
                        color = ColorStyle.jianshu.primaryDark
                    }
                }.show(this@MainActivity)
            }) {

            }
            
        }
    }


}