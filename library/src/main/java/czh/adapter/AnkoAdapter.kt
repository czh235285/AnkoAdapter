package czh.adapter

import android.content.Context
import android.util.SparseArray
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import czh.adapter.holer.AnkoViewHolder
import czh.adapter.layout.FrameMatchUI
import czh.adapter.layout.FrameUI
import org.jetbrains.anko.AnkoComponent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.annotation.IntRange
import czh.adapter.entity.MultiItem


abstract class AnkoAdapter<E>(data: List<E>?) : RecyclerView.Adapter<AnkoViewHolder>() {
    var mData: MutableList<E>


    init {
        mData = data?.toMutableList() ?: arrayListOf()
    }

    protected lateinit var mContext: Context

    protected var onItemClickListener: OnItemClickListener<E>? = null
    protected var onItemLongClickListener: OnItemLongClickListener<E>? = null

    //header footer
    private var mHeaderLayout: LinearLayout? = null
    private var mFooterLayout: LinearLayout? = null

    //empty
    private var mEmptyLayout: FrameLayout? = null
    private var mIsUseEmpty = true

    protected var mHeadAndEmptyEnable: Boolean = false
    var mFootAndEmptyEnable: Boolean = false

    fun setHeaderFooterEmpty(isHeadAndEmpty: Boolean, isFootAndEmpty: Boolean) {
        mHeadAndEmptyEnable = isHeadAndEmpty
        mFootAndEmptyEnable = isFootAndEmpty
    }

    protected fun getEmptyViewCount(): Int = when {
        mEmptyLayout == null || mEmptyLayout!!.childCount == 0 || !mIsUseEmpty || mData.isNotEmpty() -> 0
        else -> 1
    }

    protected fun getHeaderLayoutCount(): Int = if (mHeaderLayout == null || mHeaderLayout?.childCount == 0) 0 else 1

    protected fun getFooterLayoutCount(): Int = if (mFooterLayout == null || mFooterLayout?.childCount == 0) 0 else 1


    fun getHeaderCount(): Int {
        return mHeaderLayout?.childCount ?: 0
    }

    fun getFooterCount(): Int {
        return mFooterLayout?.childCount ?: 0
    }

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
            return getHeaderLayoutCount() + mData.size
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
            count = getHeaderLayoutCount() + mData.size + getFooterLayoutCount()
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
            position - getHeaderLayoutCount() < mData.size -> {
                (mData[position - getHeaderLayoutCount()] as? MultiItem)?.itemType ?: -0x11111111
            }
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

    /**
     * @param footer
     * @param orientation
     */
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
            mHeaderLayout?.parent?.let {
                (it as FrameLayout).removeAllViews()
            }
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
            mFooterLayout?.parent?.let {
                (it as FrameLayout).removeAllViews()
            }
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
        mHeaderLayout?.parent?.let {
            (it as FrameLayout).removeAllViews()
        }
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
        mFooterLayout?.parent?.let {
            (it as FrameLayout).removeAllViews()
        }
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
            HEADER_VIEW, FOOTER_VIEW -> AnkoViewHolder(FrameUI(), parent.context)
            else -> AnkoViewHolder(ankoLayout(viewType), parent.context).apply {
                itemView?.setOnClickListener {
                    onItemClickListener?.onItemClick(it, layoutPosition - getHeaderLayoutCount(), getItem(layoutPosition - getHeaderLayoutCount())!!)
                }

                itemView?.setOnLongClickListener {
                    onItemLongClickListener?.onItemLongClick(it, layoutPosition - getHeaderLayoutCount(), getItem(layoutPosition - getHeaderLayoutCount())!!)
                    return@setOnLongClickListener true
                }
            }
        }
    }


    override fun onBindViewHolder(holder: AnkoViewHolder, position: Int) {
        when (getItemViewType(position)) {
            EMPTY_VIEW -> (holder.ui as FrameMatchUI).empty.apply {
                if (childCount == 0) {
                    addView(mEmptyLayout)
                }
            }
            HEADER_VIEW -> (holder.ui as FrameUI).empty.apply {
                if (childCount == 0) {
                    addView(mHeaderLayout)
                }
            }
            FOOTER_VIEW -> (holder.ui as FrameUI).empty.apply {
                if (childCount == 0) {
                    addView(mFooterLayout)
                }
            }
            else -> convert(holder, position - getHeaderLayoutCount(), getItem(position - getHeaderLayoutCount()))
        }
    }

    override fun onViewAttachedToWindow(holder: AnkoViewHolder) {
        super.onViewAttachedToWindow(holder)
        val type = holder.itemViewType
        if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW) {
            setFullSpan(holder)
        }
    }

    protected fun setFullSpan(holder: AnkoViewHolder) {
        if (holder.itemView.layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val params = holder
                    .itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            params.isFullSpan = true
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            val defSpanSizeLookup = manager.spanSizeLookup
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val type = getItemViewType(position)
                    return if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW) {
                        manager.spanCount
                    } else {
                        defSpanSizeLookup.getSpanSize(position)
                    }
                }
            }
        }
    }

    private fun getItem(@IntRange(from = 0) position: Int): E? {
        return if (position < mData.size)
            mData[position]
        else
            null
    }

    /**
     * 刷新数据
     */
    fun replaceData(data: List<E>?) {
        // 不是同一个引用才清空列表
        if (mData !== data) {
            mData = data?.toMutableList() ?: arrayListOf()
        }
        notifyDataSetChanged()
    }


    /**
     * 加载更多
     */
    fun addData(data: List<E>?) {
        data?.toMutableList()?.let {
            mData.addAll(it)
            notifyItemRangeInserted(mData.size - data.size + getHeaderLayoutCount(), data.size)
            compatibilityDataSizeChanged(data.size)
        }
    }

    /**
     * 加载更多
     */
    fun addData(data: E) {
        mData.add(data)
        notifyItemInserted(mData.size + getHeaderLayoutCount())
        compatibilityDataSizeChanged(1)
    }

    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = if (mData == null) 0 else mData.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }

    /**
     * item点击事件监听
     */
    interface OnItemClickListener<E> {
        fun onItemClick(view: View, position: Int, item: E)
    }

    /**
     * item长按事件监听
     */
    interface OnItemLongClickListener<E> {
        fun onItemLongClick(view: View, position: Int, item: E): Boolean
    }


    fun setOnItemClickListener(action: (view: View, position: Int, item: E) -> Unit) {
        onItemClickListener = object : OnItemClickListener<E> {
            override fun onItemClick(view: View, position: Int, item: E) {
                action(view, position, item)
            }
        }
    }

    fun setOnItemLongClickListener(action: (view: View, position: Int, item: E) -> Unit) {
        onItemLongClickListener = object : OnItemLongClickListener<E> {
            override fun onItemLongClick(view: View, position: Int, item: E): Boolean {
                action(view, position, item)
                return true
            }
        }
    }

    protected abstract fun convert(holder: AnkoViewHolder, position: Int, item: E?)

    companion object {
        const val EMPTY_VIEW = 0x00000111
        const val HEADER_VIEW = 0x00000222
        const val FOOTER_VIEW = 0x00000333
    }
}
        