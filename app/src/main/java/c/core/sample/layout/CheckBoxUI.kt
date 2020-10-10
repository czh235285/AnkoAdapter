package c.core.sample.layout

import android.content.Context
import android.view.Gravity
import android.widget.CheckBox
import android.widget.TextView
import org.jetbrains.anko.*

/**
 *  author : czh
 *  date : 2020/9/29 11:08
 *  description : 
 */
class CheckBoxUI : AnkoComponent<Context> {
    lateinit var cb: CheckBox
    lateinit var tv: TextView

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        frameLayout {
            lparams(-1)
            tv = textView {
                text="选择"
                textSize = 20f
                gravity = Gravity.CENTER
            }.lparams {
            }

            cb = checkBox {

            }.lparams {
                gravity = Gravity.END
            }
        }
    }
}