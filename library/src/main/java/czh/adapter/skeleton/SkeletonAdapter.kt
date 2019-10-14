package czh.adapter.skeleton

import android.content.Context
import czh.adapter.AnkoAdapter
import czh.adapter.holer.AnkoViewHolder
import czh.adapter.layout.ShimmerUI
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

class SkeletonAdapter(mData: List<AnkoComponent<Context>>?) : AnkoAdapter<AnkoComponent<Context>>(mData) {
    override fun ankoLayout(viewType: Int): AnkoComponent<Context> {
        return ShimmerUI()
    }

    override fun convert(holder: AnkoViewHolder, position: Int, item: AnkoComponent<Context>?) = with(holder.ui as ShimmerUI) {
        item?.createView(AnkoContext.create(mContext))
        shimmer.removeAllViews()
        shimmer.addView(item?.createView(AnkoContext.create(mContext)))
        shimmer.startShimmerAnimation()
    }
}