package com.github.daniilbug.lifeisbetter.utils

import android.view.View

fun View.showWithScale(duration: Long = 300L) {
    if (visibility == View.VISIBLE) return
    scaleY = 0f
    scaleX = 0f
    visibility = View.VISIBLE
    animate().scaleY(1f).scaleX(1f).setDuration(duration).start()
}


fun View.hideWithScale(duration: Long = 300L) {
    if (visibility == View.INVISIBLE) return
    animate().scaleY(0f).scaleX(0f).setDuration(duration).withEndAction {
        visibility = View.INVISIBLE
    }.start()
}
