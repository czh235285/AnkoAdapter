package c.core.adapter.layout

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*

class AnKoLoadMoreViewUI : AnkoComponent<Context> {
    lateinit var loadCompleteView: TextView
    lateinit var loadEndView: TextView
    lateinit var loadFailView: TextView
    lateinit var loadingView: LinearLayout

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        frameLayout {
            lparams(-1, dip(40))
            loadingView = linearLayout {
                gravity = Gravity.CENTER
                progressBar {
                    indeterminateTintList = ColorStateList.valueOf(Color.parseColor("#22bb62"))
                }
                textView("正在加载中...").lparams {
                    leftMargin = dip(4)
                }
            }.lparams(-1, -1)

            loadFailView = textView("加载失败,点击重试") {
                gravity = Gravity.CENTER
            }.lparams(-1, -1)

            loadCompleteView = textView("点击加载更多") {
                gravity = Gravity.CENTER
            }.lparams(-1, -1)

            loadEndView = textView("已经到底啦~") {
                gravity = Gravity.CENTER
            }.lparams(-1, -1)
        }
    }
}