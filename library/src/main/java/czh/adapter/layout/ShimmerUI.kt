package czh.adapter.layout

import android.content.Context
import android.view.ViewManager
import io.supercharge.shimmerlayout.ShimmerLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.shimmerLayout(theme: Int = 0, init: ShimmerLayout.() -> Unit): ShimmerLayout {
    return ankoView({ ShimmerLayout(it) }, theme, init)
}
class ShimmerUI : AnkoComponent<Context> {

    lateinit var shimmer: ShimmerLayout

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        shimmer = shimmerLayout {
            setShimmerAnimationDuration(2500)
        }
        shimmer
    }
}