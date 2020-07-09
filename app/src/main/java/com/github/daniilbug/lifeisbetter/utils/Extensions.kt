package com.github.daniilbug.lifeisbetter.utils

import android.animation.Animator
import android.view.View

fun View.showWithScale(duration: Long = 300L) {
    scaleY = 0f
    scaleX = 0f
    visibility = View.VISIBLE
    animate().scaleY(1f).scaleX(1f).setDuration(duration).start()
}
