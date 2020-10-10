package c.core.adapter.skeleton

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import c.core.adapter.AnkoAdapter
import c.core.adapter.dslItem
import c.core.adapter.entity.SkeletonMultiple
import c.core.adapter.layout.ShimmerUI
import me.samlss.broccoli.Broccoli
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import java.util.*

class Skeleton {
    private var durationMillis: Int = 1500
    private var isUsePlaceholder: Boolean = true
    private var recyclerView: RecyclerView? = null
    private var mActualAdapter: RecyclerView.Adapter<*>? = null
    private val mSkeletonAdapter by lazy {
        AnkoAdapter()
    }
    private val mList by lazy {
        mutableListOf<SkeletonMultiple>()
    }

    fun adapter(adapter: RecyclerView.Adapter<*>): Skeleton {
        this.mActualAdapter = adapter
        return this
    }

    fun add(ui: AnkoComponent<Context>, count: Int): Skeleton {
        (0 until count).forEach {
            this.mList.add(SkeletonMultiple(1).apply {
                anKoUI = ui
            })
        }
        return this
    }

    fun add(resId: Int, count: Int): Skeleton {
        (0 until count).forEach {
            this.mList.add(SkeletonMultiple(2).apply {
                this.resId = resId
            })
        }
        return this
    }


    /**
     * 是否自动映射布局，占位控件。为false时需要自己传入自定义占位布局
     * @param isUsePlaceholder Boolean
     * @return Skeleton
     */
    fun setIsUsePlaceholder(isUsePlaceholder: Boolean): Skeleton {
        this.isUsePlaceholder = isUsePlaceholder
        return this
    }

    fun setDuration(durationMillis: Int): Skeleton {
        this.durationMillis = durationMillis
        return this
    }


    @SuppressLint("ClickableViewAccessibility")
    fun show(): Skeleton {

        mSkeletonAdapter.replaceData(mList.map {
            dslItem<ShimmerUI> { holder, position ->
                val mContext = holder.itemView.context
                it.anKoUI?.createView(AnkoContext.create(mContext))?.also {
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

                it.resId?.also {
                    val view = LayoutInflater.from(mContext).inflate(it, null)
                    if (isUsePlaceholder) {
                        val broccoli = Broccoli()
                        val stack = ArrayDeque<View>()
                        stack.addLast(view)
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
                    shimmer.addView(view)
                    shimmer.setShimmerAnimationDuration(durationMillis)
                    shimmer.startShimmerAnimation()
                }

            }
        })
        recyclerView?.adapter = mSkeletonAdapter
        recyclerView?.setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun hide(): Skeleton {
        recyclerView?.adapter = mActualAdapter
        recyclerView?.setOnTouchListener { v, event ->
            return@setOnTouchListener false
        }
        return this
    }

    companion object {
        fun bind(recyclerView: RecyclerView): Skeleton {
            val skeleton = Skeleton()
            skeleton.recyclerView = recyclerView
            return skeleton
        }
    }
}