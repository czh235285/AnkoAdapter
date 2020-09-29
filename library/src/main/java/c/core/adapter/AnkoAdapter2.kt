package c.core.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import c.core.adapter.holer.AnkoViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import c.core.adapter.entity.AdapterItem


class AnkoAdapter2() : RecyclerView.Adapter<AnkoViewHolder>() {
    var mData: MutableList<AdapterItem>
    var mRecyclerView: RecyclerView? = null


    init {
        mData = arrayListOf()
    }


    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnkoViewHolder {
        Log.d("测试","onCreateViewHolder")
        return AnkoViewHolder(mData[viewType].itemType, parent.context)
    }


    override fun onBindViewHolder(holder: AnkoViewHolder, position: Int) {
        mData[position].itemBind.invoke(holder, position)
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

    protected fun setFullSpan(holder: AnkoViewHolder) {
        if (holder.itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val params = holder
                    .itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            params.isFullSpan = true
        }
    }


    /**
     * 刷新数据
     */
    fun replaceData(data: List<AdapterItem>?) {
        // 不是同一个引用才清空列表
        if (mData !== data) {
            mData = data?.toMutableList() ?: arrayListOf()
        }
        notifyDataSetChanged()
    }


    /**
     * 加载更多
     */
    fun addData(data: List<AdapterItem>?) {
        data?.toMutableList()?.let {
            mData.addAll(it)
            notifyItemRangeInserted(mData.size - data.size, data.size)
            compatibilityDataSizeChanged(data.size)
        }
    }

    /**
     * 加载更多
     */
    fun addData(data: AdapterItem) {
        mData.add(data)
        notifyItemInserted(mData.size)
        compatibilityDataSizeChanged(1)
    }

    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = if (mData == null) 0 else mData.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }
}
        