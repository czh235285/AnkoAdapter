package com.czh.adapter

import android.content.Context
import czh.adapter.AnkoAdapter
import czh.adapter.holer.AnkoViewHolder
import org.jetbrains.anko.AnkoComponent

class DemoAdapter(mData: List<String>?) : AnkoAdapter<String>(mData) {
    override fun ankoLayout(viewType: Int): AnkoComponent<Context> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convert(holder: AnkoViewHolder, position: Int, item: String?) {

    }

}

