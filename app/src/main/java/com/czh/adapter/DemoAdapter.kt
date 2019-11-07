package com.czh.adapter

import android.content.Context
import com.czh.adapter.layout.SkeletonTestUI
import czh.adapter.AnkoAdapter
import czh.adapter.holer.AnkoViewHolder
import org.jetbrains.anko.AnkoComponent

class DemoAdapter(mData: List<String>?) : AnkoAdapter<String>(mData) {
    override fun ankoLayout(viewType: Int): AnkoComponent<Context> {
        return SkeletonTestUI()
    }

    override fun convert(holder: AnkoViewHolder, position: Int, item: String?) = with(holder.ui as SkeletonTestUI) {
        tv.text = item ?: ""
    }

}

