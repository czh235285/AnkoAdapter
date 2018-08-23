package com.czh.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

class TestAdapter2(val list: List<String>) : RecyclerView.Adapter<TestAdapter2.ViewHoler<DemoAdapterUI>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler<DemoAdapterUI> {
        return ViewHoler(DemoAdapterUI(), parent.context)
    }

    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: ViewHoler<DemoAdapterUI>, position: Int) {
        holder.ui.tv.text=list[position]
    }


    class ViewHoler<T : AnkoComponent<Context>>(var ui: T, ctx: Context) : RecyclerView.ViewHolder(ui.createView(AnkoContext.create(ctx)))
}