package com.czh.adapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DensityUtils.setCustomDensity(this, application)
        startActivity<MultActivity>()
        setContentView(R.layout.activity_main)

        rcv.layoutManager = LinearLayoutManager(this)
        val adapter = TestAdapter4(arrayListOf("1", "2", "3")).apply {
            val ui = DemoAdapterUI().createView(AnkoContext.create(this@MainActivity))
            ui.find<TextView>(1).text = "空布局"
            setEmptyView(ui)
        }
        rcv.adapter = adapter
        tv1.setOnClickListener {
            adapter.replaceData(null)
        }

        tv2.setOnClickListener {
            val ui = DemoAdapterUI().createView(AnkoContext.create(this@MainActivity))
            ui.find<TextView>(1).text = "头部"
            adapter.addHeaderView(ui)
        }
        tv3.setOnClickListener {
            val ui = DemoAdapterUI().createView(AnkoContext.create(this@MainActivity))
            ui.find<TextView>(1).text = "尾部"
            adapter.addFooterView(ui)
        }
        tv4.setOnClickListener {
            adapter.addData(arrayListOf("添加数据"))
        }
        tv5.setOnClickListener {
            adapter.removeAllHeaderView()
        }
        tv6.setOnClickListener {
            adapter.removeAllFooterView()
        }
    }
}
