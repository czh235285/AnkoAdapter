package com.czh.adapter.activity

import com.meibei.app.ui.v9.base.AnkoActivity
import com.czh.adapter.layout.TestActivityUI
import org.jetbrains.anko.setContentView


class TestActivity : AnkoActivity() {
    val ui = TestActivityUI()

    override fun ankoLayout() {
        ui.setContentView(this)
    }

    override fun afterInitView() {

    }

}