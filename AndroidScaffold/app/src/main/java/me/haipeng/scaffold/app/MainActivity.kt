@file:Suppress("SpellCheckingInspection")

package me.haipeng.scaffold.app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import me.haipeng.scaffold.app.databinding.ActivityMainBinding
import me.haipeng.scaffold.core.activity.ButtonsActivity
import me.haipeng.scaffold.core.activity.ButtonsActivity.ButtonsBuilder.Companion.LAYOUT_STYLE_LINEAR
import me.haipeng.scaffold.core.dimension.dip
import me.haipeng.scaffold.core.masonry.aligns
import me.haipeng.scaffold.core.masonry.constraint.side.toBottomOf
import me.haipeng.scaffold.core.masonry.constraint.side.toEndOf
import me.haipeng.scaffold.core.masonry.constraint.side.toStartOf
import me.haipeng.scaffold.core.style.*

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

        ColorStyle.default = ColorStyle.zhihu

        binding.composeView.setContent {
            Sense()
        }

//        ComposeView(this).apply {
//            binding.rootView.addView(this)
//            aligns {
//                top toBottomOf binding.test
//                start toStartOf parent
//                end toEndOf parent
//            }
//            setContent {
//                Sense
//            }
//        }
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
                        title {
                            color = Color.WHITE
                            text = "ButtonsActivity示例"
                        }

                        backgroundColor = ColorStyle.default.primary
                        icon {
                            enable = true
                        }
                    }
                    statusBar {
                        color = ColorStyle.default.primaryDark
                    }

                    buttons {
                        layoutStyle = LAYOUT_STYLE_LINEAR
                        margin = dip(16F)
                        button {
                            elevation = dip(4F)
                            title {
                                text = "Button 1"
                                color = TextColorStyle.white.normal
                            }
                            background {
                                color = ColorStyle.default.primary
                                radius = dip(8F)
                                stroke {
                                    color = Color.BLACK
                                    width = 0
                                }
                            }
                            onClick {
                                // handle your click event
                            }
                        }
                        button {
                            title {
                                text = "From path to path no man in sight"
                            }
                            onClick {/* handle your click event */}
                        }
                    }

                }.show(this@MainActivity)
            }) {

            }
            
        }
    }


}