package com.czh.adapter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import czh.adapter.AnkoMultiAdapter
import czh.adapter.holer.AnkoViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class MultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DensityUtils.setCustomDensity(this, application)
        setContentView(R.layout.activity_main)

        rcv.layoutManager = LinearLayoutManager(this)


        val mList: MutableList<Mult> = arrayListOf()
        mList.add(Mult(1))
        mList.add(Mult(2))

        val adapter = object : AnkoMultiAdapter<Mult>(mList) {
            init {
                addType(1, DemoAdapterUI())
                addType(2, DemoAdapterUI2())
            }


            override fun ankoLayout(viewType: Int): AnkoComponent<Context> {
                return when (viewType) {
                    1 -> DemoAdapterUI()
                    else -> DemoAdapterUI2()
                }
            }


            override fun convert(holder: AnkoViewHolder, position: Int, item: Mult?) {

            }

        }.apply {
            val ui = DemoAdapterUI().createView(AnkoContext.create(this@MultActivity))
            ui.find<TextView>(1).text = "空布局"
            setEmptyView(ui)
            setOnItemClickListener { view, position, item ->
                toast("点击：" + position.toString())
            }
            setOnItemLongClickListener { view, position, item ->
                toast("长按：" + position.toString())
            }
        }
        rcv.adapter = adapter
        tv1.setOnClickListener {
            adapter.replaceData(null)
        }

        tv2.setOnClickListener {
            val ui = DemoAdapterUI().createView(AnkoContext.create(this@MultActivity))
            ui.find<TextView>(1).text = "头部"
            adapter.addHeaderView(ui)
        }
        tv3.setOnClickListener {
            val ui = DemoAdapterUI().createView(AnkoContext.create(this@MultActivity))
            ui.find<TextView>(1).text = "尾部"
            adapter.addFooterView(ui)
        }
        tv4.setOnClickListener {
            adapter.addData(Mult(1))
        }
        tv5.setOnClickListener {
            adapter.removeAllHeaderView()
        }
        tv6.setOnClickListener {
            adapter.removeAllFooterView()
        }
    }
}
