package com.czh.adapter

import android.content.Context

import org.jetbrains.anko.AnkoComponent

import czh.adapter.SimpAnkoAdapter
import czh.adapter.holer.AnkoViewHolder

class TestAdapter4(data: List<String>) : SimpAnkoAdapter<String>(data) {

    override fun ankoLayout(viewType: Int): AnkoComponent<Context> {
        return DemoAdapterUI()
    }

    override fun convert(holder: AnkoViewHolder, position: Int, item: String?) {
        (holder.ui as DemoAdapterUI).tv.text=item
    }
}
