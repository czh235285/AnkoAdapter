package c.core.adapter.skeleton

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import c.core.adapter.entity.SkeletonMultiple
import org.jetbrains.anko.AnkoComponent

class Skeleton {
    private var recyclerView: RecyclerView? = null
    private var mActualAdapter: RecyclerView.Adapter<*>? = null
    private val mSkeletonAdapter: SkeletonAdapter by lazy {
        SkeletonAdapter(null)
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
        mSkeletonAdapter.isUsePlaceholder = isUsePlaceholder
        return this
    }

    fun setDuration(durationMillis: Int): Skeleton {
        mSkeletonAdapter.durationMillis = durationMillis
        return this
    }


    @SuppressLint("ClickableViewAccessibility")
    fun show(): Skeleton {
        mSkeletonAdapter.replaceData(mList)
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