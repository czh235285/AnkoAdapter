package com.czh.adapter

import czh.adapter.holer.BaseViewHolder
import czh.adapter.JsonAdapter
import org.json.JSONArray
import org.json.JSONObject

import kotlinx.android.synthetic.main.item.view.*

class TestAdapter(mLayoutResId: Int, mData: JSONArray?) : JsonAdapter(mLayoutResId, mData) {
    override fun convert(holder: BaseViewHolder, item: JSONObject?) {
        holder.itemView.run {
            item?.optString("name")?.let { tvName.text = it }
        }
    }
}
