package com.github.seisuke.akmk.sample

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.github.seisuke.akmk.constraintLayout
import com.github.seisuke.akmk.constraints
import org.jetbrains.anko.*

class MainActivity : Activity(), AnkoComponent<Context> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createView(AnkoContext.create(this)))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        constraintLayout {
            val button1 = button {
                id = View.generateViewId()
                text = "1"
            }

            val button2 = button {
                id = View.generateViewId()
                text = "2"
            }

            val button3 = button {
                id = View.generateViewId()
                text = "3"
            }

            val button4 = button {
                id = View.generateViewId()
                text = "4"
            }.lparams(width = matchConstraint)

            val button5 = button {
                id = View.generateViewId()
                text = "5"
            }.lparams(width = dip(50), height = 0)

            button {
                id = View.generateViewId()
                text = "6"
            }.lparams {
                circularPosition(button5, dip(100), 135.0f)
            }

            val textView1 = textView {
                id = View.generateViewId()
                text = "bottom"
                backgroundColor = Color.GRAY
                gravity = Gravity.BOTTOM
            }.lparams( height = dip(50), width = dip(50))

            val textView2 = textView {
                id = View.generateViewId()
                text = "top"
                backgroundColor = Color.GRAY
                gravity = Gravity.TOP
            }.lparams( height = dip(50), width = dip(50))

            constraints({
                guidelineBegin(dip(50)) - button1
                button1 - dip(30) % button2 - dip(30) % button3
                alignTail(button1, button2, button3)
                parent - button4 - parent
                parent - textView1 - textView2
                textView2.connectBaseline(textView1)
            }, {
                parent - chainPacked(button1, button4, button5) - parent
                button4.alignWith(button1)
                button5.alignWith(button1, button2)
                button5.ratio("h,1:2")

                parent - dip(50) % textView1
            })

        }
    }
}
