package czh.adapter.holer

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

open class AnkoViewHolder(var ui:AnkoComponent<Context>,ctx:Context) : RecyclerView.ViewHolder(ui.createView(AnkoContext.create(ctx)))
