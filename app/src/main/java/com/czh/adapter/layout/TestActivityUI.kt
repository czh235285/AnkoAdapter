package com.czh.adapter.layout

import android.support.v7.widget.RecyclerView
import android.view.View
import com.czh.adapter.activity.TestActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout

class TestActivityUI : AnkoComponent<TestActivity> {
    lateinit var rcv: RecyclerView

    override fun createView(ui: AnkoContext<TestActivity>): View = with(ui) {
        verticalLayout {
            rcv = recyclerView {

            }
        }
    }
}