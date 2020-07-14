package c.core.adapter.holer

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

open class AnkoViewHolder(private var ui: AnkoComponent<Context>, ctx: Context) : RecyclerView.ViewHolder(ui.createView(AnkoContext.create(ctx))) {
    fun <T : AnkoComponent<Context>> getAnKoUi(): T? {
        return ui as? T
    }
}
