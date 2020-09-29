package c.core.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import c.core.adapter.holer.AnkoViewHolder
import c.core.adapter.layout.FrameMatchUI
import c.core.adapter.layout.FrameUI
import org.jetbrains.anko.AnkoComponent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.annotation.IntRange
import c.core.adapter.entity.MultiItem
import c.core.adapter.loadmore.AnKoBaseLoadMoreModule
import c.core.adapter.loadmore.AnkoLoadMoreModule

/**
 * 获取模块
 */
interface AnKoAdapterModuleImp {
    fun addLoadMoreModule(baseQuickAdapter: AnkoAdapter<*>): AnKoBaseLoadMoreModule {
        return AnKoBaseLoadMoreModule(baseQuickAdapter)
    }
}

abstract class AnkoAdapter<E>(data: List<E>?) : RecyclerView.Adapter<AnkoViewHolder>(), AnKoAdapterModuleImp {
    var mData: MutableList<E>
    var mRecyclerView: RecyclerView? = null

    internal var mBaseLoadMoreModule: AnKoBaseLoadMoreModule? = null

    /**
     * 加载更多模块
     */
    val loadMoreModule: AnKoBaseLoadMoreModule
        get() {
            checkNotNull(mBaseLoadMoreModule) { "Please first implements AnkoLoadMoreModule" }
            return mBaseLoadMoreModule!!
        }


    init {
        mData = data?.toMutableList() ?: arrayListOf()
        checkModule()
    }

    /**
     * 检查模块
     */
    private fun checkModule() {
        if (this is AnkoLoadMoreModule) {
            mBaseLoadMoreModule = this.addLoadMoreModule(this)
        }
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

    fun getEmptyViewCount(): Int = when {
        mEmptyLayout == null || mEmptyLayout!!.childCount == 0 || !mIsUseEmpty || mData.isNotEmpty() -> 0
        else -> 1
    }

    fun getHeaderLayoutCount(): Int = if (mHeaderLayout == null || mHeaderLayout?.childCount == 0) 0 else 1

    fun getFooterLayoutCount(): Int = if (mFooterLayout == null || mFooterLayout?.childCount == 0) 0 else 1


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
        if (getEmptyViewCount() == 1) {
            var count = 1
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() == 1) {
                count++
            }
            if (mFootAndEmptyEnable && getFooterLayoutCount() == 1) {
                count++
            }
            return count
        } else {
            val loadMoreCount = if (mBaseLoadMoreModule?.hasLoadMoreView() == true) {
                1
            } else {
                0
            }
            return getHeaderLayoutCount() + mData.size + getFooterLayoutCount() + loadMoreCount
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (getEmptyViewCount() == 1) {
            val header = mHeadAndEmptyEnable && getHeaderLayoutCount() == 1
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

        val hasHeader = getHeaderLayoutCount() == 1
        if (hasHeader && position == 0) {
            return HEADER_VIEW
        } else {
            var adjPosition = if (hasHeader) {
                position - 1
            } else {
                position
            }
            val dataSize = mData.size
            return if (adjPosition < dataSize) {
                (mData[position - getHeaderLayoutCount()] as? MultiItem)?.itemType ?: DEFAULT_VIEW
            } else {
                adjPosition -= dataSize
                val numFooters = if (getFooterLayoutCount() == 1) {
                    1
                } else {
                    0
                }
                if (adjPosition < numFooters) {
                    FOOTER_VIEW
                } else {
                    LOAD_MORE_VIEW
                }
            }
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
            LOAD_MORE_VIEW -> {
                val ui = mBaseLoadMoreModule!!.loadMoreView.getRootView()
                AnkoViewHolder(ui, parent.context).also {
                    mBaseLoadMoreModule!!.setupViewHolder(it)
                }
            }
            EMPTY_VIEW -> AnkoViewHolder(FrameMatchUI(), parent.context)
            HEADER_VIEW, FOOTER_VIEW -> AnkoViewHolder(FrameUI(), parent.context)
            else -> AnkoViewHolder(ankoLayout(viewType), parent.context).apply {
                itemView.setOnClickListener {
                    onItemClickListener?.onItemClick(it, layoutPosition - getHeaderLayoutCount(), getItem(layoutPosition - getHeaderLayoutCount())!!)
                }

                itemView.setOnLongClickListener {
                    onItemLongClickListener?.onItemLongClick(it, layoutPosition - getHeaderLayoutCount(), getItem(layoutPosition - getHeaderLayoutCount())!!)
                    return@setOnLongClickListener true
                }
            }
        }
    }


    override fun onBindViewHolder(holder: AnkoViewHolder, position: Int) {
        mBaseLoadMoreModule?.autoLoadMore(position)
        when (getItemViewType(position)) {
            LOAD_MORE_VIEW -> {
                mBaseLoadMoreModule?.let {
                    it.loadMoreView.convert(holder, position, it.loadMoreStatus)
                }
            }
            EMPTY_VIEW -> holder.getAnKoUi<FrameMatchUI>()?.apply {
                if (empty.childCount == 0) {
                    empty.addView(mEmptyLayout)
                }
            }
            HEADER_VIEW -> holder.getAnKoUi<FrameUI>()?.apply {
                if (empty.childCount == 0) {
                    empty.addView(mHeaderLayout)
                }
            }
            FOOTER_VIEW -> holder.getAnKoUi<FrameUI>()?.apply {
                if (empty.childCount == 0) {
                    empty.addView(mFooterLayout)
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
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
        mBaseLoadMoreModule?.reset()
        if (mData !== data) {
            mData = data?.toMutableList() ?: arrayListOf()
        }
        notifyDataSetChanged()
        mBaseLoadMoreModule?.checkDisableLoadMoreIfNotFullPage()
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
        const val EMPTY_VIEW = -0x00000111
        const val HEADER_VIEW = -0x00000222
        const val FOOTER_VIEW = -0x00000333
        const val LOAD_MORE_VIEW = -0x00000444
        const val DEFAULT_VIEW = -0x00000555
    }
}
        