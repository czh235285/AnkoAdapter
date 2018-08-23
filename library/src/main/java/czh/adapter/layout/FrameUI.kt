package czh.adapter.layout

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import org.jetbrains.anko.*

class FrameUI : AnkoComponent<Context> {
    lateinit var empty: FrameLayout

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        empty = frameLayout {
            lparams(matchParent, wrapContent)
        }
        empty
    }
}