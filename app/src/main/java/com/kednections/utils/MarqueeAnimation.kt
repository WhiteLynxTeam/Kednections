package com.kednections.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView

fun startMarquee(textView: TextView, container: View, speed: Long = 5000L) {

    container.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            container.viewTreeObserver.removeOnGlobalLayoutListener(this)
            val textWidth = textView.width
            val containerWidth = container.width

            val startX = -textWidth.toFloat()
            val endX = containerWidth.toFloat()

            fun animate() {
                textView.translationX = startX

                val animator = ObjectAnimator.ofFloat(textView, "translationX", startX, endX)
                animator.duration = speed
                animator.interpolator = null // linear
                animator.start()

                animator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator) {
                        animate()
                    }

                    override fun onAnimationStart(animation: Animator) {}
                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })
            }

            animate()
        }
    })


}