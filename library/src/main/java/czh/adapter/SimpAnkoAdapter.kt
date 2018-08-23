package czh.adapter

import android.content.Context
import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import czh.adapter.holer.AnkoViewHolder
import czh.adapter.layout.FrameMatchUI
import czh.adapter.layout.FrameUI
import org.jetbrains.anko.AnkoComponent

abstract class SimpAnkoAdapter<E>(var data: List<E>) : RecyclerView.Adapter<AnkoViewHolder>() {
    protected lateinit var mContext: Context

    //header footer
    private var mHeaderLayout: LinearLayout? = null
    private var mFooterLayout: LinearLayout? = null

    //empty
    private var mEmptyLayout: FrameLayout? = null
    private var mIsUseEmpty = true

    private var mHeadAndEmptyEnable: Boolean = false
    private var mFootAndEmptyEnable: Boolean = false

    fun setHeaderFooterEmpty(isHeadAndEmpty: Boolean, isFootAndEmpty: Boolean) {
        mHeadAndEmptyEnable = isHeadAndEmpty
        mFootAndEmptyEnable = isFootAndEmpty
    }

    private fun getEmptyViewCount(): Int = when {
        mEmptyLayout == null || mEmptyLayout!!.childCount == 0 || !mIsUseEmpty || data.isNotEmpty() -> 0
        else -> 1
    }

    private fun getHeaderLayoutCount(): Int = if (mHeaderLayout == null || mHeaderLayout?.childCount == 0) 0 else 1

    private fun getFooterLayoutCount(): Int = if (mFooterLayout == null || mFooterLayout?.childCount == 0) 0 else 1


    private fun getHeaderViewPosition(): Int {
        if (getEmptyViewCount() == 1) {
            if (mHeadAndEmptyEnable) {
                return 0
            }
        } else {
            return 0
        }
        return -1
    }

    private fun getFooterViewPosition(): Int {
        if (getEmptyViewCount() == 1) {
            var position = 1
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                position++
            }
            if (mFootAndEmptyEnable) {
                return position
            }
        } else {
            return getHeaderLayoutCount() + data.size
        }
        return -1
    }


    override fun getItemCount(): Int {
        var count: Int
        if (getEmptyViewCount() == 1) {
            count = 1
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                count++
            }
            if (mFootAndEmptyEnable && getFooterLayoutCount() != 0) {
                count++
            }
        } else {
            count = getHeaderLayoutCount() + data.size + getFooterLayoutCount()
        }
        return count
    }


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
            position - getHeaderLayoutCount() < data.size -> super.getItemViewType(position)
            else -> FOOTER_VIEW
        }

    }


    /**
     * @param emptyView
     */
    fun setEmptyView(emptyView: View) {
        var insert = false
        if (mEmptyLayout == null) {
            mEmptyLayout = FrameLayout(emptyView.context)
            mEmptyLayout?.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams
                    .MATCH_PARENT).apply {
                emptyView.layoutParams?.let {
                    width = it.width
                    height = it.height
                }
            }
            insert = true
        }
        mEmptyLayout?.removeAllViews()
        mEmptyLayout?.addView(emptyView)
        mIsUseEmpty = true
        if (insert) {
            if (getEmptyViewCount() == 1) {
                val position = 0
                notifyItemInserted(position)
            }
        }
    }

    /**
     * @param header
     * @param orientation
     */
    fun addHeaderView(header: View, orientation: Int = LinearLayout.VERTICAL): Int {
        if (mHeaderLayout == null) {
            mHeaderLayout = LinearLayout(header.context)
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout?.orientation = LinearLayout.VERTICAL
                mHeaderLayout?.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                mHeaderLayout?.orientation = LinearLayout.HORIZONTAL
                mHeaderLayout?.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        val index = mHeaderLayout!!.childCount
        mHeaderLayout?.addView(header, index)
        if (mHeaderLayout?.childCount == 1) {
            val position = getHeaderViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return index
    }


    fun addFooterView(footer: View, orientation: Int = LinearLayout.VERTICAL): Int {
        if (mFooterLayout == null) {
            mFooterLayout = LinearLayout(footer.context)
            mFooterLayout?.orientation = orientation
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout?.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                mFooterLayout?.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        val index = mFooterLayout!!.childCount
        mFooterLayout?.addView(footer, index)
        if (mFooterLayout?.childCount == 1) {
            val position = getFooterViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return index
    }


    /**
     * 移除 header
     * @param header
     */
    fun removeHeaderView(header: View) {
        if (getHeaderLayoutCount() == 0) return

        mHeaderLayout?.removeView(header)
        if (mHeaderLayout?.childCount == 0) {
            val position = getHeaderViewPosition()
            if (position != -1) {
                notifyItemRemoved(position)
            }
        }
    }


    /**
     * 移除 footer
     * @param footer
     */
    fun removeFooterView(footer: View) {
        if (getFooterLayoutCount() == 0) return

        mFooterLayout?.removeView(footer)
        if (mFooterLayout?.childCount == 0) {
            val position = getFooterViewPosition()
            if (position != -1) {
                notifyItemRemoved(position)
            }
        }
    }


    /**
     * 移除 所有HeaderView
     */
    fun removeAllHeaderView() {
        if (getHeaderLayoutCount() == 0) return

        mHeaderLayout?.removeAllViews()
        val position = getHeaderViewPosition()
        if (position != -1) {
            notifyItemRemoved(position)
        }
    }

    /**
     * 移除 所有FooterView
     */
    fun removeAllFooterView() {
        if (getFooterLayoutCount() == 0) return

        mFooterLayout?.removeAllViews()
        val position = getFooterViewPosition()
        if (position != -1) {
            notifyItemRemoved(position)
        }
    }


    abstract fun ankoLayout(viewType: Int): AnkoComponent<Context>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnkoViewHolder {
        this.mContext = parent.context
        return when (viewType) {
            EMPTY_VIEW -> AnkoViewHolder(FrameMatchUI(), parent.context)
            HEADER_VIEW, FOOTER_VIEW-> AnkoViewHolder(FrameUI(), parent.context)
            else -> AnkoViewHolder(ankoLayout(viewType), parent.context)
        }
    }


    override fun onBindViewHolder(holder: AnkoViewHolder, position: Int) {
        when (getItemViewType(position)) {
            EMPTY_VIEW -> (holder.ui as FrameMatchUI).empty.addView(mEmptyLayout)
            HEADER_VIEW -> (holder.ui as FrameUI).empty.addView(mHeaderLayout)
            FOOTER_VIEW -> (holder.ui as FrameUI).empty.addView(mFooterLayout)
            else -> convert(holder, position - getHeaderLayoutCount(), getItem(position - getHeaderLayoutCount()))
        }
    }

    private fun getItem(@IntRange(from = 0) position: Int): E? {
        return if (position < data.size)
            data[position]
        else
            null
    }

    protected abstract fun convert(holder: AnkoViewHolder, position: Int, item: E?)

    companion object {
        const val EMPTY_VIEW = 0x00000111
        const val HEADER_VIEW = 0x00000222
        const val FOOTER_VIEW = 0x00000333
    }
}
        