package czh.adapter.entity

import android.content.Context
import org.jetbrains.anko.AnkoComponent

class SkeletonMultiple(override val itemType: Int) : MultiItem {
    var anKoUI: AnkoComponent<Context>? = null
    var resId: Int? = null
}