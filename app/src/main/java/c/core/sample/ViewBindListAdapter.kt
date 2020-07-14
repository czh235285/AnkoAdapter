package c.core.sample

import c.core.adapter.BaseListAdapter
import c.core.adapter.holer.BaseViewHolder
import c.core.sample.databinding.FootBinding
import c.core.sample.databinding.ItemBinding

class ViewBindListAdapter(data: MutableList<MultipleBean>) : BaseListAdapter<MultipleBean>(data) {

    override fun ui(viewType: Int): Int {
//        return R.layout.item
        return when (viewType) {
            1 -> R.layout.foot
            else -> R.layout.item
        }
    }

    override fun convert(holder: BaseViewHolder, position: Int, item: MultipleBean?) {
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