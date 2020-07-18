package c.core.adapter.loadmore

import android.content.Context
import android.view.View
import c.core.adapter.holer.AnkoViewHolder
import c.core.adapter.layout.AnKoLoadMoreViewUI
import org.jetbrains.anko.AnkoComponent

class AnKoSimpleLoadMoreView : AnKoLoadMoreView() {
    override fun getRootView(): AnkoComponent<Context> {
        return AnKoLoadMoreViewUI()
    }

    override fun getLoadingView(holder: AnkoViewHolder): View? {
        return holder.getAnKoUi<AnKoLoadMoreViewUI>()?.loadingView
    }

    override fun getLoadComplete(holder: AnkoViewHolder): View? {
        return holder.getAnKoUi<AnKoLoadMoreViewUI>()?.loadCompleteView
    }

    override fun getLoadEndView(holder: AnkoViewHolder): View? {
        return holder.getAnKoUi<AnKoLoadMoreViewUI>()?.loadEndView
    }

    override fun getLoadFailView(holder: AnkoViewHolder): View? {
        return holder.getAnKoUi<AnKoLoadMoreViewUI>()?.loadFailView
    }
}