package com.czh.adapter.layout

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.czh.adapter.R
import com.czh.adapter.colorString
import com.czh.adapter.dp
import com.czh.adapter.setShape
import org.jetbrains.anko.*

class SkeletonTestUI : AnkoComponent<Context> {

    lateinit var tv: TextView

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            lparams(matchParent)
            gravity = Gravity.CENTER
            tv = textView("测试一下呢") {
                textSize = 20f
                setShape("#cccc00", 10f)
                setPadding(20, 20, 20, 20)
                colorString("#ffffff")
                gravity = Gravity.CENTER
            }.lparams (dp(300)){
                setMargins(20, 20, 20, 20)
            }

            imageView(R.mipmap.ic_launcher){
            }.lparams(dp(60),dp(60))
        }
    }
}