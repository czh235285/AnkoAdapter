package com.czh.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

class TestAdapter3(val list: List<String>,val ctx: Context) : RecyclerView.Adapter<TestAdapter3.ViewHoler>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        return ViewHoler(DemoAdapterUI(), ctx)
    }

    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        with(holder.ui as DemoAdapterUI){
            tv.text=list[position]
        }
    }


    class ViewHoler(var ui: AnkoComponent<Context>, ctx: Context) : RecyclerView.ViewHolder(ui.createView(AnkoContext.create(ctx)))
}