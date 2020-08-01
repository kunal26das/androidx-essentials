import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import kotlin.math.hypot

object Animations {

    fun View.circularReveal() {
        val cx = width / 2
        val cy = height / 2
        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.VISIBLE
            }
        })
        anim.start()
    }

    fun View.circularConceal() {
        val cx = width / 2
        val cy = height / 2
        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, initialRadius, 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.GONE
            }
        })
        anim.start()
    }
}