package com.czh.adapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.czh.adapter.layout.SkeletonTestUI
import czh.adapter.skeleton.Skeleton
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DensityUtils.setCustomDensity(this, application)
        setContentView(R.layout.activity_main)

        rcv.layoutManager = LinearLayoutManager(this)
        val adapter = DemoAdapter(arrayListOf("1", "2", "3", "4")).apply {
            SkeletonTestUI().also {
                val view = it.createView(AnkoContext.create(this@MainActivity))
                it.tv.text = "空布局"
                setEmptyView(view)
            }
        }
        rcv.adapter = adapter
        adapter.IsNoMore=true
        SkeletonTestUI().also {
            val view = it.createView(AnkoContext.create(this@MainActivity))
            it.tv.text = "没有更多"
            adapter.setNoMoreView(view)
        }
        tv1.setOnClickListener {
            adapter.replaceData(null)
        }

        tv2.setOnClickListener {
            SkeletonTestUI().also {
                val view = it.createView(AnkoContext.create(this@MainActivity))
                it.tv.text = "头部"
                adapter.addHeaderView(view)
            }
        }
        tv3.setOnClickListener {
            SkeletonTestUI().also {
                val view = it.createView(AnkoContext.create(this@MainActivity))
                it.tv.text = "尾部"
                adapter.addFooterView(view)
            }
        }
        tv4.setOnClickListener {
            adapter.addData(arrayListOf("添加数据"))
        }
        tv5.setOnClickListener {
            adapter.removeAllHeaderView()
        }
        tv6.setOnClickListener {
//            adapter.removeAllFooterView()
            adapter.IsNoMore=!adapter.IsNoMore
        }


        //骨架屏测试
        val skeleton = Skeleton
                .bind(rcv)
//                .setIsUsePlaceholder(false)
                .setDuration(1500)
                .adapter(adapter)
                .add(SkeletonTestUI(), 4)
                .show()

        //骨架屏取消
        Handler().postDelayed({
            skeleton.hide()
        }, 4000)
    }
}
