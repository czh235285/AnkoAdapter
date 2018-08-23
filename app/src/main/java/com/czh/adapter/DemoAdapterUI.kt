package com.czh.adapter

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import org.jetbrains.anko.*

class DemoAdapterUI : AnkoComponent<Context> {
    lateinit var tv: TextView

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            tv = textView("测试") {
                textSize = 20f
                setShape("#cccc00", 10f)
                setPadding(20, 20, 20, 20)
                colorString("#ffffff")
                gravity = Gravity.CENTER
            }.lparams(dip(180)) {

                setMargins(20, 20, 20, 20)
            }

        }
    }
}