package com.czh.adapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DensityUtils.setCustomDensity(this, application)
        setContentView(R.layout.activity_main)

        rcv.layoutManager=LinearLayoutManager(this)
        rcv.adapter = TestAdapter4(arrayListOf()).apply {
            val ui=DemoAdapterUI().createView(AnkoContext.create(this@MainActivity))
            ui.find<TextView>(1).text="空布局"
            setEmptyView(ui)
            addHeaderView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
            addHeaderView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
            setHeaderFooterEmpty(true,false)
        }

    }
}
