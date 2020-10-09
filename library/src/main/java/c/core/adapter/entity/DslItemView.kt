package c.core.adapter.entity

import android.content.Context
import c.core.adapter.holer.AnkoViewHolder
import org.jetbrains.anko.AnkoComponent

/**
 *  author : czh
 *  date : 2020/9/27 10:33
 *  description : 
 */
class DslItemView {
    lateinit var ui: AnkoComponent<Context>

    var data: Any? = null

    var spanSize = 1

    var itemBind: (holder: AnkoViewHolder, position: Int) -> Unit = { holder, position ->

    }

    fun bind(action: (holder: AnkoViewHolder, position: Int) -> Unit) {
        itemBind = action
    }
}