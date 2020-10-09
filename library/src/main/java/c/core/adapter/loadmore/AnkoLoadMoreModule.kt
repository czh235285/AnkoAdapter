package c.core.adapter.loadmore

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import c.core.adapter.AnkoAdapter
import c.core.adapter.BaseListAdapter
import c.core.adapter.holer.AnkoViewHolder


/**
 * 需要【向下加载更多】功能的，[AnkoAdapter]继承此接口
 */
interface AnkoLoadMoreModule

object LoadMoreModuleConfigAnKo {

    /**
     * 设置全局的LodeMoreView
     */
    @JvmStatic
    var defLoadMoreView: AnKoLoadMoreView = AnKoSimpleLoadMoreView()
}

/**
 * 加载更多基类
 */
open class AnKoBaseLoadMoreModule(private val BaseListAdapter: AnkoAdapter) : LoadMoreListenerImp {

    private var mLoadMoreListener: OnLoadMoreListener? = null

    /** 不满一屏时，是否可以继续加载的标记位 */
    private var mNextLoadEnable = true

    var loadMoreStatus = LoadMoreStatus.Complete
        private set

    var isLoadEndMoreGone: Boolean = false
        private set

    /** 设置加载更多布局 */
    var loadMoreView = LoadMoreModuleConfigAnKo.defLoadMoreView

    /** 加载完成后是否允许点击 */
    var enableLoadMoreEndClick = false

    /** 是否打开自动加载更多 */
    var isAutoLoadMore = true

    /** 当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多 */
    var isEnableLoadMoreIfNotFullPage = true

    /**
     * 预加载
     */
    var preLoadNumber = 1
        set(value) {
            if (value > 1) {
                field = value
            }
        }

    /**
     * 是否加载中
     */
    val isLoading: Boolean
        get() {
            return loadMoreStatus == LoadMoreStatus.Loading
        }

    /**
     * Gets to load more locations
     *
     * @return
     */
    private val loadMoreViewPosition: Int
        get() {
            if (BaseListAdapter.getEmptyViewCount() == 1) {
                return -1
            }
            return BaseListAdapter.let {
                it.getHeaderLayoutCount() + it.mData.size + it.getFooterLayoutCount()
            }
        }

    /**
     * 是否打开加载更多
     */
    var isEnableLoadMore = false
        set(value) {
            val oldHasLoadMore = hasLoadMoreView()
            field = value
            val newHasLoadMore = hasLoadMoreView()

            if (oldHasLoadMore) {
                if (!newHasLoadMore) {
                    BaseListAdapter.notifyItemRemoved(loadMoreViewPosition)
                }
            } else {
                if (newHasLoadMore) {
                    loadMoreStatus = LoadMoreStatus.Complete
                    BaseListAdapter.notifyItemInserted(loadMoreViewPosition)
                }
            }
        }


    internal fun setupViewHolder(viewHolder: AnkoViewHolder) {
        viewHolder.itemView.setOnClickListener {
            if (loadMoreStatus == LoadMoreStatus.Fail) {
                loadMoreToLoading()
            } else if (loadMoreStatus == LoadMoreStatus.Complete) {
                loadMoreToLoading()
            } else if (enableLoadMoreEndClick && loadMoreStatus == LoadMoreStatus.End) {
                loadMoreToLoading()
            }
        }
    }

    /**
     * The notification starts the callback and loads more
     */
    fun loadMoreToLoading() {
        if (loadMoreStatus == LoadMoreStatus.Loading) {
            return
        }
        loadMoreStatus = LoadMoreStatus.Loading
        BaseListAdapter.notifyItemChanged(loadMoreViewPosition)
        invokeLoadMoreListener()
    }


    fun hasLoadMoreView(): Boolean {
        if (mLoadMoreListener == null || !isEnableLoadMore) {
            return false
        }
        if (loadMoreStatus == LoadMoreStatus.End && isLoadEndMoreGone) {
            return false
        }
        return BaseListAdapter.mData.isNotEmpty()
    }

    /**
     * 自动加载数据
     * @param position Int
     */
    internal fun autoLoadMore(position: Int) {
        if (!isAutoLoadMore) {
            //如果不需要自动加载更多，直接返回
            return
        }
        if (!hasLoadMoreView()) {
            return
        }
        if (position < BaseListAdapter.itemCount - preLoadNumber) {
            return
        }
        if (loadMoreStatus != LoadMoreStatus.Complete) {
            return
        }
        if (loadMoreStatus == LoadMoreStatus.Loading) {
            return
        }
        if (!mNextLoadEnable) {
            return
        }

        invokeLoadMoreListener()
    }

    /**
     * 触发加载更多监听
     */
    private fun invokeLoadMoreListener() {
        loadMoreStatus = LoadMoreStatus.Loading
        BaseListAdapter.mRecyclerView?.let {
            it.post { mLoadMoreListener?.onLoadMore() }
        } ?: mLoadMoreListener?.onLoadMore()
    }

    /**
     * check if full page after [BaseListAdapter.setNewInstance] [BaseListAdapter.setList],
     * if full, it will enable load more again.
     *
     * 用来检查数据是否满一屏，如果满足条件，再开启
     *
     */
    fun checkDisableLoadMoreIfNotFullPage() {
        if (isEnableLoadMoreIfNotFullPage) {
            return
        }
        // 先把标记位设置为false
        mNextLoadEnable = false
        val recyclerView = BaseListAdapter.mRecyclerView ?: return
        val manager = recyclerView.layoutManager ?: return
        if (manager is LinearLayoutManager) {
            recyclerView.postDelayed({
                if (isFullScreen(manager)) {
                    mNextLoadEnable = true
                }
            }, 50)
        } else if (manager is StaggeredGridLayoutManager) {
            recyclerView.postDelayed({
                val positions = IntArray(manager.spanCount)
                manager.findLastCompletelyVisibleItemPositions(positions)
                val pos = getTheBiggestNumber(positions) + 1
                if (pos != BaseListAdapter.itemCount) {
                    mNextLoadEnable = true
                }
            }, 50)
        }
    }

    private fun isFullScreen(llm: LinearLayoutManager): Boolean {
        return (llm.findLastCompletelyVisibleItemPosition() + 1) != BaseListAdapter.itemCount ||
                llm.findFirstCompletelyVisibleItemPosition() != 0
    }

    private fun getTheBiggestNumber(numbers: IntArray?): Int {
        var tmp = -1
        if (numbers == null || numbers.isEmpty()) {
            return tmp
        }
        for (num in numbers) {
            if (num > tmp) {
                tmp = num
            }
        }
        return tmp
    }

    /**
     * Refresh end, no more data
     *
     * @param gone if true gone the load more view
     */
    @JvmOverloads
    fun loadMoreEnd(gone: Boolean = false) {
        if (!hasLoadMoreView()) {
            return
        }
//        mNextLoadEnable = false
        isLoadEndMoreGone = gone

        loadMoreStatus = LoadMoreStatus.End

        if (gone) {
            BaseListAdapter.notifyItemRemoved(loadMoreViewPosition)
        } else {
            BaseListAdapter.notifyItemChanged(loadMoreViewPosition)
        }
    }

    /**
     * Refresh complete
     */
    fun loadMoreComplete() {
        if (!hasLoadMoreView()) {
            return
        }
//        mNextLoadEnable = true
        loadMoreStatus = LoadMoreStatus.Complete

        BaseListAdapter.notifyItemChanged(loadMoreViewPosition)

        checkDisableLoadMoreIfNotFullPage()
    }

    /**
     * Refresh failed
     */
    fun loadMoreFail() {
        if (!hasLoadMoreView()) {
            return
        }
        loadMoreStatus = LoadMoreStatus.Fail
        BaseListAdapter.notifyItemChanged(loadMoreViewPosition)
    }

    /**
     * 设置加载监听事件
     * @param listener OnLoadMoreListener?
     */
    override fun setOnLoadMoreListener(listener: OnLoadMoreListener?) {
        this.mLoadMoreListener = listener
        isEnableLoadMore = true
    }

    /**
     * 重置状态
     */
    internal fun reset() {
        if (mLoadMoreListener != null) {
            isEnableLoadMore = true
            loadMoreStatus = LoadMoreStatus.Complete
        }
    }
}