package c.core.sample

import android.content.Context
import c.core.adapter.AnkoAdapter
import c.core.adapter.holer.AnkoViewHolder
import c.core.sample.layout.ImageViewUI
import c.core.sample.layout.TextViewUI
import org.jetbrains.anko.AnkoComponent

class AnKoSampleAdapter(mData: List<MultipleBean>?) : AnkoAdapter<MultipleBean>(mData) {
    override fun ankoLayout(viewType: Int): AnkoComponent<Context> {
//        这里是多type布局，如果单个type直接return一个布局就行.多type的实体MultipleBean继承MultiItem即可。
//        return ImageViewUI()
        return when (viewType) {
            MultipleBean.TEXT -> TextViewUI()
            else -> ImageViewUI()
        }
    }

    override fun convert(holder: AnkoViewHolder, position: Int, item: MultipleBean?) {
        when (holder.itemViewType) {
            MultipleBean.TEXT -> {
                holder.getAnKoUi<TextViewUI>()?.apply {
                    tv.text = item?.text
                }
            }
            MultipleBean.IMG -> {
                holder.getAnKoUi<ImageViewUI>()?.apply {
                    iv.load(item?.url)
                }
            }
        }

    }
}

