package c.core.sample.layout

import android.content.Context
import android.widget.ImageView
import c.core.sample.dp
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.imageView
import org.jetbrains.anko.verticalLayout

class ImageViewUI : AnkoComponent<Context> {
    lateinit var iv: ImageView

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            lparams(-1)
            iv = imageView {
                scaleType=ImageView.ScaleType.CENTER_CROP
            }.lparams(-1, 300.dp)
        }
    }
}