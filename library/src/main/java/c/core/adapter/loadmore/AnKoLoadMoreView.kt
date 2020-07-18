package c.core.adapter.loadmore

import android.content.Context
import android.view.View
import c.core.adapter.holer.AnkoViewHolder
import org.jetbrains.anko.AnkoComponent


/**
 * 继承此类，实行自定义loadMore视图
 */
abstract class AnKoLoadMoreView {

    /**
     * 根布局
     * @param parent ViewGroup
     * @return View
     */
    abstract fun getRootView(): AnkoComponent<Context>

    /**
     * 布局中的 加载更多视图
     * @param holder AnkoViewHolder
     * @return View
     */
    abstract fun getLoadingView(holder: AnkoViewHolder): View?

    /**
     * 布局中的 加载完成布局
     * @param holder AnkoViewHolder
     * @return View
     */
    abstract fun getLoadComplete(holder: AnkoViewHolder): View?

    /**
     * 布局中的 加载结束布局
     * @param holder AnkoViewHolder
     * @return View
     */
    abstract fun getLoadEndView(holder: AnkoViewHolder): View?

    /**
     * 布局中的 加载失败布局
     * @param holder AnkoViewHolder
     * @return View
     */
    abstract fun getLoadFailView(holder: AnkoViewHolder): View?

    /**
     * 可重写此方式，实行自定义逻辑
     * @param holder AnkoViewHolder
     * @param position Int
     * @param loadMoreStatus LoadMoreStatus
     */
    open fun convert(holder: AnkoViewHolder, position: Int, loadMoreStatus: LoadMoreStatus) {
        when (loadMoreStatus) {
            LoadMoreStatus.Complete -> {
                getLoadingView(holder)?.isVisible(false)
                getLoadComplete(holder)?.isVisible(true)
                getLoadFailView(holder)?.isVisible(false)
                getLoadEndView(holder)?.isVisible(false)
            }
            LoadMoreStatus.Loading -> {
                getLoadingView(holder)?.isVisible(true)
                getLoadComplete(holder)?.isVisible(false)
                getLoadFailView(holder)?.isVisible(false)
                getLoadEndView(holder)?.isVisible(false)
            }
            LoadMoreStatus.Fail -> {
                getLoadingView(holder)?.isVisible(false)
                getLoadComplete(holder)?.isVisible(false)
                getLoadFailView(holder)?.isVisible(true)
                getLoadEndView(holder)?.isVisible(false)
            }
            LoadMoreStatus.End -> {
                getLoadingView(holder)?.isVisible(false)
                getLoadComplete(holder)?.isVisible(false)
                getLoadFailView(holder)?.isVisible(false)
                getLoadEndView(holder)?.isVisible(true)
            }
        }
    }

    private fun View.isVisible(visible: Boolean) {
        this.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

