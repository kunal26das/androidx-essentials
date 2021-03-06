package androidx.essentials.core.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.essentials.core.injector.KoinComponent.inject
import kotlin.math.roundToInt

object Resources {

    private val resources by inject<Resources>()
    private val applicationContext by inject<Context>()

    val Int.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), resources.displayMetrics
        ).roundToInt()

    val Float.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this, resources.displayMetrics
        )

    val HEIGHT_STATUS_BAR = resources.getDimensionPixelSize(
        resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
    )

    fun getMenu(@MenuRes menuRes: Int): Menu? = PopupMenu(applicationContext, null).apply {
        MenuInflater(applicationContext).inflate(menuRes, menu)
    }.menu

}
