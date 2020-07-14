package c.core.sample.layout

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import c.core.sample.R
import c.core.sample.dp
import org.jetbrains.anko.*

class TextViewUI : AnkoComponent<Context> {

    lateinit var tv: TextView

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            lparams(matchParent)
            tv = textView {
                textSize = 20f
                gravity = Gravity.CENTER
            }.lparams(-1, 60.dp)
        }
    }
}