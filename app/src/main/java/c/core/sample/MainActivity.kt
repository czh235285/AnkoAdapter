package c.core.sample

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.core.adapter.*
import c.core.sample.layout.TextViewUI
import c.core.widget.recyclerView
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private lateinit var rcv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(frameLayout {
            rcv = recyclerView {
                layoutManager = LinearLayoutManager(context)
            }.lparams(-1, -1)

            tv = textView {
                text = "添加数据"
                horizontalPadding = 12
                verticalPadding = 6
                backgroundColor = Color.parseColor("#fff000")
            }.lparams {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
                rightMargin = 30
            }

        })

        val mvals = mutableListOf("安卓", "IOS", "H5")


//        rcv.submitItems(mvals.map {
//            adapterItem<TextViewUI> { holder, position ->
//                tv.text = it
//            }
//        },2)
//
//        tv.setOnClickListener {
//            rcv.submitItems(mvals.map {
//                adapterItem<TextViewUI> { holder, position ->
//                    tv.text = it
//                }
//            }, 2)
//        }

        rcv.dslAdapter {
            

            addItem<TextViewUI> { holder, position ->
                tv.text = "动态添加"
            }
            addItem<TextViewUI> { holder, position ->
                tv.text = "动态添加"
            }
            addItem<TextViewUI> { holder, position ->
                tv.text = "动态添加"
            }
            addItem<TextViewUI> { holder, position ->
                tv.text = "动态添加"
            }
        }

    }

}
