package c.core.widget

import android.view.View
import android.view.ViewManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.custom.ankoView


/**
 * recyclerView
 */
fun ViewManager.recyclerView(theme: Int = 0) = recyclerView(theme) {

}

/**
 * recyclerView
 */
inline fun ViewManager.recyclerView(
        theme: Int = 0,
        init: RecyclerView.() -> Unit
): RecyclerView {
    return ankoView({
        RecyclerView(it).apply {
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }, theme, init)
}
