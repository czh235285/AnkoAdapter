package c.core.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.core.adapter.AnkoAdapter2
import c.core.adapter.entity.AdapterItem
import c.core.sample.layout.ImageViewUI
import c.core.sample.layout.TextViewUI
import c.core.widget.recyclerView
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {

    private lateinit var rcv: RecyclerView

    private val mAdapter by lazy {
        SampleAdapterAnko(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(verticalLayout {
            rcv = recyclerView {
                layoutManager = LinearLayoutManager(this.context)
            }
        })
        rcv.adapter = AnkoAdapter2().apply {
            repeat(100) {
                val data = it

                addData(AdapterItem().apply {
                    itemType = TextViewUI()
                    itemBind = { itemHolder, itemPosition ->
                        itemHolder.bind<TextViewUI> {
                            tv.text = "测试${data}"
                            tv.setOnClickListener {
                                toast("测试${data}")
                            }

                        }
                    }
                })

//                addData(AdapterItem().apply {
//                    itemType = ImageViewUI()
//                    itemBind = { itemHolder, itemPosition ->
//                        itemHolder.bind<ImageViewUI> {
//                            iv.load("https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3238317745,514710292&fm=26&gp=0.jpg")
//                        }
//                    }
//                })
            }


        }

        mAdapter.replaceData(data)
        mAdapter.loadMoreModule.apply {

        }
    }

    private val data: MutableList<MultipleBean>
        get() = mutableListOf<MultipleBean>().apply {
            add(MultipleBean(MultipleBean.TEXT).apply {
                text = "文字布局1"
            })
            add(MultipleBean(MultipleBean.IMG).apply {
                url = "https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3238317745,514710292&fm=26&gp=0.jpg"
            })
            add(MultipleBean(MultipleBean.TEXT).apply {
                text = "文字布局2"
            })
            add(MultipleBean(MultipleBean.TEXT).apply {
                text = "文字布局3"
            })
            add(MultipleBean(MultipleBean.IMG).apply {
                url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2083557659,850952849&fm=26&gp=0.jpg"
            })
            add(MultipleBean(MultipleBean.TEXT).apply {
                text = "文字布局4"
            })
            add(MultipleBean(MultipleBean.TEXT).apply {
                text = "文字布局5"
            })
        }
}
