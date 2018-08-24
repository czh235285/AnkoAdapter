package com.czh.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.CheckBox
import android.widget.TextView
import org.jetbrains.anko.*

class DemoAdapterUI : AnkoComponent<Context> {
    lateinit var tv: TextView

   lateinit var cb: CheckBox

    @SuppressLint("ResourceType")
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            lparams(matchParent, wrapContent)
            tv = textView("测试") {
                id=1
                setOnClickListener {
                    cb.isChecked=!cb.isChecked
                }
                textSize = 20f
                setShape("#cccc00", 10f)
                setPadding(20, 20, 20, 20)
                colorString("#ffffff")
                gravity = Gravity.CENTER
            }.lparams(dip(180)) {

                setMargins(20, 20, 20, 20)
            }

            cb=checkBox()
        }
    }
}