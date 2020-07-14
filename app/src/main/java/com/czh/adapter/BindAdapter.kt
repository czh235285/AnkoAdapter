package com.czh.adapter

import com.czh.adapter.databinding.FootBinding
import com.czh.adapter.databinding.ItemBinding
import czh.adapter.BaseAdapter
import czh.adapter.holer.BaseViewHolder

class BindAdapter(data: MutableList<Type>) : BaseAdapter<Type>(data) {

    override fun ui(viewType: Int): Int {
//        return R.layout.item
        return when (viewType) {
            1 -> R.layout.foot
            else -> R.layout.item
        }
    }

    override fun convert(holder: BaseViewHolder, position: Int, item: Type?) {
        when (holder.itemViewType) {
            1 -> FootBinding.bind(holder.itemView).apply {
                tv.text = "type1"

            }

            2 -> ItemBinding.bind(holder.itemView).apply {
                tvName.text = "type2"
            }
        }
    }
}