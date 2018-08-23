package com.czh.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.backgroundDrawable

fun View.setShape(solidColor: String? = null, radius: Float? = null, strokeWidth: Int? = null, strokeColor: String = "#cccccc") {
    this.backgroundDrawable = GradientDrawable().apply {
        gradientType = GradientDrawable.RECTANGLE
        solidColor?.let {
            setColor(Color.parseColor(it))
        }
        radius?.let {
            cornerRadius = it
        }
        strokeWidth?.let {
            setStroke(it, Color.parseColor(strokeColor))
        }

    }
}


fun View.dp(dipValue: Int) : Int{
    val scale =context.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}


fun TextView.colorString(textColor: String) {
    setTextColor(Color.parseColor(textColor))
}