package c.core.sample

import c.core.adapter.entity.MultiItem


class MultipleBean(override val itemType: Int) : MultiItem {
    var text: String? = null
    var url: String? = null

    companion object {
        const val TEXT = 1
        const val IMG = 2
    }
}