package c.core.sample

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.load(
    url: Any?,
    placeholder: Int? = R.mipmap.ic_launcher,
    error: Int? = R.mipmap.ic_launcher
) {
    Glide.with(context).load(url)
        .apply(
            RequestOptions().apply {
                placeholder?.let { placeholder(it) }
                error?.let { error(it) }
            }
        ).into(this)
}
