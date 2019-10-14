package czh.adapter.skeleton

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.AnkoComponent

class Skeleton {
    var recyclerView: RecyclerView? = null
    var mActualAdapter: RecyclerView.Adapter<*>? = null
    val mSkeletonAdapter: SkeletonAdapter by lazy {
        SkeletonAdapter(null)
    }
    val mList by lazy {
        mutableListOf<AnkoComponent<Context>>()
    }

    fun adapter(adapter: RecyclerView.Adapter<*>): Skeleton {
        this.mActualAdapter = adapter
        return this
    }

    fun add(ui: AnkoComponent<Context>, count: Int): Skeleton {
        (0 until count).forEach {
            this.mList.add(ui)
        }
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