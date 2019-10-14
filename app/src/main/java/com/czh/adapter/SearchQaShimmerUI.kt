package com.czh.adapter

import android.content.Context
import android.graphics.Color
import org.jetbrains.anko.*
fun wPx(px: Int): Int = (wProportion * px).toInt()

fun hPx(px: Int): Int = (hProportion * px).toInt()

val wProportion by lazy {
    App.ctx.screenWidth().toFloat() / 750
}
val hProportion by lazy {
    App.ctx.screenHeight().toFloat() / 1334
}
/**
 * 获取屏幕宽度
 *
 * @return 屏幕宽度
 */
fun Context.screenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 *
 * @return 屏幕高度
 */
fun Context.screenHeight(): Int {
    return resources.displayMetrics.heightPixels
}


class SearchQaShimmerUI : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {

        relativeLayout {
            lparams(matchParent, wPx(273))
            view {
                backgroundColor = Color.parseColor("#e5e5e5")
            }.lparams(wPx(400), wPx(44)) {
                topMargin = wPx(32)
                leftMargin = wPx(72)
                rightMargin = wPx(24)
            }
            view {
                backgroundColor = Color.parseColor("#e5e5e5")
            }.lparams(wPx(654), wPx(84)) {
                leftMargin = wPx(72)
                topMargin = wPx(100)
                rightMargin = wPx(24)
            }

            view {
                backgroundColor = Color.parseColor("#e5e5e5")
            }.lparams(wPx(160), wPx(33)) {
                topMargin = wPx(208)
                leftMargin = wPx(72)
            }
            view {
                backgroundColor = Color.parseColor("#e5e5e5")
            }.lparams(wPx(160), wPx(33)) {
                topMargin = wPx(208)
                leftMargin = wPx(300)
            }


        }
    }
}