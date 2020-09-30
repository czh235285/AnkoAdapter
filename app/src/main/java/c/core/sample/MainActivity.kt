package c.core.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.core.adapter.DslAdapter
import c.core.dslAdapter
import c.core.dslItem
import c.core.linear
import c.core.sample.layout.ImageViewUI
import c.core.sample.layout.TextViewUI
import c.core.widget.recyclerView
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {

    private lateinit var rcv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(verticalLayout {
            rcv = recyclerView {
            }
        })
        val imageUrl = "https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3238317745,514710292&fm=26&gp=0.jpg"


        rcv.linear().dslAdapter {
            repeat(50) {

                dslItem<TextViewUI> { holder, position ->
                    tv.text = "测试文字布局$position"

                    holder.itemView.setOnClickListener {
                        toast("点击position==》$position")
                    }
                }

                dslItem<ImageViewUI> { holder, position ->
                    iv.load(imageUrl)
                }
            }
        }

    }
}
