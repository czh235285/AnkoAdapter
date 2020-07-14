package c.core.sample

import org.jetbrains.anko.dip

inline val Int.dp: Int get() = App.ctx.dip(this)
inline val Int.dpf: Float get() = this * App.ctx.resources.displayMetrics.density
