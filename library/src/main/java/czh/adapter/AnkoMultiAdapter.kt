package czh.adapter

import android.content.Context
import android.util.SparseArray
import org.jetbrains.anko.AnkoComponent


abstract class AnkoMultiAdapter<E : AnkoMultiAdapter.MultiItem> : AnkoAdapter<E> {
    var mLayouts: SparseArray<AnkoComponent<Context>> = SparseArray()

    constructor(mData: MutableList<E>?) : super(mData)

    override fun getItemViewType(position: Int): Int {
        if (getEmptyViewCount() == 1) {
            val header = mHeadAndEmptyEnable && getHeaderLayoutCount() != 0
            return when (position) {
                0 -> if (header) {
                    HEADER_VIEW
                } else {
                    EMPTY_VIEW
                }
                1 -> if (header) {
                    EMPTY_VIEW
                } else {
                    FOOTER_VIEW
                }
                2 -> FOOTER_VIEW
                else -> EMPTY_VIEW
            }
        }
        return when {
            position < getHeaderLayoutCount() -> HEADER_VIEW
            position - getHeaderLayoutCount() < mData.size -> (mData[position - getHeaderLayoutCount()] as MultiItem).itemType
            else -> FOOTER_VIEW
        }

    }


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnkoViewHolder {
//        this.mContext = parent.context
//        return when (viewType) {
//            EMPTY_VIEW -> AnkoViewHolder(FrameMatchUI(), parent.context)
//            HEADER_VIEW, FOOTER_VIEW -> AnkoViewHolder(FrameUI(), parent.context)
//            else -> AnkoViewHolder(ankoLayout(viewType), parent.context).apply {
//                itemView?.setOnClickListener {
//                    onItemClickListener?.onItemClick(it, layoutPosition - getHeaderLayoutCount(), getItem(layoutPosition - getHeaderLayoutCount())!!)
//                }
//
//                itemView?.setOnLongClickListener {
//                    onItemLongClickListener?.onItemLongClick(it, layoutPosition - getHeaderLayoutCount(), getItem(layoutPosition - getHeaderLayoutCount())!!)
//                    return@setOnLongClickListener true
//                }
//            }
//        }
//    }
//
//    override fun onBindViewHolder(holder: AnkoViewHolder, position: Int) {
//        when (holder.itemViewType) {
//            HEADER_VIEW, FOOTER_VIEW, EMPTY_VIEW -> {
//                super.onBindViewHolder(holder, position)
//            }
//            else -> {
//                ankoLayout((mData[position - getHeaderLayoutCount()] as MultiItem).itemType).let {
//                    convert(holder, position - getHeaderLayoutCount(), getItem(position - getHeaderLayoutCount()))
//                }
//
//            }
//        }
//    }


    protected fun addType(type: Int, layoutResId: AnkoComponent<Context>) {
        mLayouts.put(type, layoutResId)
    }

    interface MultiItem {
        val itemType: Int
    }
}

