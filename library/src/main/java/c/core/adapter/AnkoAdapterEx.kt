package c.core.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import c.core.adapter.entity.DslItemView
import c.core.adapter.holer.AnkoViewHolder
import org.jetbrains.anko.AnkoComponent


inline fun <reified T : AnkoComponent<Context>> adapterItem(crossinline action: T.(holder: AnkoViewHolder, position: Int) -> Unit): DslItemView {

    DslItemView().let {
        it.ui = T::class.java.newInstance()
        it.itemBind = { holder, position ->
            holder.getAnKoUi<T>()?.let { action(it, holder, position) }
        }
        return it
    }
}


inline fun <reified T : AnkoComponent<Context>> AnkoAdapter.addItem(crossinline action: T.(holder: AnkoViewHolder, position: Int) -> Unit): DslItemView {
    return DslItemView().also {
        it.ui = T::class.java.newInstance()
        it.itemBind = { holder, position ->
            holder.getAnKoUi<T>()?.let { action(it, holder, position) }
        }
        addData(it)
    }
}


fun RecyclerView.dslAdapter(init: AnkoAdapter.() -> Unit) {
    ((adapter as? AnkoAdapter) ?: AnkoAdapter().also { adapter = it }).let(init)
}


fun RecyclerView.submitItems(data: List<DslItemView>?, page: Int = 1) {
    ((adapter as? AnkoAdapter) ?: AnkoAdapter().also { adapter = it }).apply {
        if (page == 1) {
            replaceData(data)
        } else {
            addData(data)
        }
    }
}

