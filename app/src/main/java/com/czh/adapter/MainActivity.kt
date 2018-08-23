package com.czh.adapter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import czh.adapter.AnkoJsonAdapter
import czh.adapter.holer.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoComponent
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DensityUtils.setCustomDensity(this, application)
        setContentView(R.layout.activity_main)
        val s = """
{
  "mLayouts":[
    {
      "name" : "张三"
    },{
      "name" : "李四"
    },{
      "name" : "王麻子"
    }
    ]
}
        """

//        var mList: MutableList<Mult> = arrayListOf()
//        mList.add(Mult(1))
//        mList.add(Mult(2))
//        mList.add(Mult(1))
//        mList.add(Mult(2))
//        mList.add(Mult(2))
//
//        val adapter =object : AnkoMultiAdapter<Mult>(mList){
//            override fun convert(holder: BaseViewHolder, ui: AnkoComponent<Context>, item: Mult?) {
//               when(ui){
//                   is DemoAdapterUI-> ui.tv.text="第一种"
//                   is DemoAdapterUI2-> ui.tv.text="第二种"
//               }
//            }
//
//            init {
//                addType(1,DemoAdapterUI())
//                addType(2,DemoAdapterUI2())
//            }
//        }
//
//        rcv.layoutManager = LinearLayoutManager(this)
//        rcv.adapter=adapter
//        adapter.setEmptyView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
//        adapter.addHeaderView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
//        adapter.addHeaderView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
//        adapter.addHeaderView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
//        adapter.addFooterView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
//        adapter.addFooterView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
//        adapter.addFooterView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))


//        rcv.adapter = DemoAdapter(DemoAdapterUI(), arrayListOf("1", "2", "3")).apply {
//            addHeaderView(DemoAdapterUI().createView(AnkoContext.create(this@MainActivity)))
//        }
        rcv.layoutManager = LinearLayoutManager(this)
        val mLayouts = JSONObject(s).getJSONArray("mLayouts")
        val adapter = object : AnkoJsonAdapter(DemoAdapterUI(), mLayouts) {
            override fun convert(holder: BaseViewHolder, ui: AnkoComponent<Context>, item: JSONObject?) {
                with(ui as DemoAdapterUI) {
                    item?.optString("name")?.let { tv.text = it }
                }
            }
        }


//    val adapter = TestAdapter(R.layout.item, mLayouts)
        adapter.setEmptyView(layoutInflater.inflate(R.layout.empty, null))

        rcv.adapter = adapter

        adapter.setHeaderFooterEmpty(true, false)

        adapter.setOnItemClickListener { view, position, item ->
            Toast.makeText(this@MainActivity, "点击了position==$position,item=${item.optString("name")}", Toast.LENGTH_LONG).show()
        }

        adapter.setOnItemLongClickListener { view, position, item ->
            Toast.makeText(this@MainActivity, "长按了position==$position,item=${item.optString("name")}", Toast.LENGTH_LONG).show()
        }
        tv1.setOnClickListener {
            adapter.replaceData(null)
        }
        tv2.setOnClickListener {
            adapter.addHeaderView(layoutInflater.inflate(R.layout.head, null))
        }
        tv3.setOnClickListener {
            adapter.addFooterView(layoutInflater.inflate(R.layout.foot, null))
        }
        tv4.setOnClickListener {
            adapter.addData(JSONObject(s).getJSONArray("mLayouts"))
        }
    }
}
