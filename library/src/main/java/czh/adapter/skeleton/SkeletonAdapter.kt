package czh.adapter.skeleton

import android.content.Context
import android.support.v7.widget.DrawableUtils
import android.util.Log
import android.view.View
import czh.adapter.AnkoAdapter
import czh.adapter.holer.AnkoViewHolder
import czh.adapter.layout.ShimmerUI
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import android.view.ViewGroup
import java.util.*
import me.samlss.broccoli.Broccoli
import me.samlss.broccoli.PlaceholderParameter


class SkeletonAdapter(mData: List<AnkoComponent<Context>>?) : AnkoAdapter<AnkoComponent<Context>>(mData) {
    var isUsePlaceholder = true
    var durationMillis = 1500
    override fun ankoLayout(viewType: Int): AnkoComponent<Context> {
        return ShimmerUI()
    }

    override fun convert(holder: AnkoViewHolder, position: Int, item: AnkoComponent<Context>?) = with(holder.ui as ShimmerUI) {
        item?.createView(AnkoContext.create(mContext))?.also {
            if (isUsePlaceholder) {
                val broccoli = Broccoli()
                val stack = ArrayDeque<View>()
                stack.addLast(it)
                while (!stack.isEmpty()) {
                    //取得栈顶
                    val top = stack.last as View
                    //出栈
                    stack.pollLast()
                    //如果为viewGroup则使子节点入栈
                    if (top is ViewGroup) {
                        val childCount = top.childCount
                        for (i in childCount - 1 downTo 0) {
                            stack.addLast(top.getChildAt(i))
                        }
                    } else {
                        broccoli.addPlaceholders(top)
                    }
                }
                broccoli.show()
            }
            shimmer.removeAllViews()
            shimmer.addView(it)
            shimmer.setShimmerAnimationDuration(durationMillis)
            shimmer.startShimmerAnimation()
        }
        return@with
    }
}