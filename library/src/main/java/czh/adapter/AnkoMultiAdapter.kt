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

    protected fun addType(type: Int, layoutResId: AnkoComponent<Context>) {
        mLayouts.put(type, layoutResId)
    }

    interface MultiItem {
        val itemType: Int
    }
}

