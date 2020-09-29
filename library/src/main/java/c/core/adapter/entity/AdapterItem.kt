package c.core.adapter.entity

import android.content.Context
import c.core.adapter.holer.AnkoViewHolder
import org.jetbrains.anko.AnkoComponent

/**
 *  author : czh
 *  date : 2020/9/27 10:33
 *  description : 
 */
class AdapterItem {
    lateinit var itemType: AnkoComponent<Context>

    var itemBind: (itemHolder: AnkoViewHolder, itemPosition: Int) -> Unit = { itemHolder, itemPosition ->
    }
}