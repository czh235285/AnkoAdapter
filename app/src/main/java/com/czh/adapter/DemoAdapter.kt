package com.czh.adapter

import android.content.Context
import czh.adapter.AnkoAdapter
import czh.adapter.holer.BaseViewHolder
import org.jetbrains.anko.AnkoComponent

class DemoAdapter(val uir: DemoAdapterUI, mData: List<String>?) : AnkoAdapter<String>(uir, mData) {
    override fun convert(holder: BaseViewHolder, ui: AnkoComponent<Context>, item: String?)= with(uir) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        tv.text = item
    }

}

