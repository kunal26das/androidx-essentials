package androidx.essentials.playground

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import kotlin.math.roundToInt

object Resources {

    private lateinit var resources: Resources
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context.applicationContext
        resources = applicationContext.resources
    }

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

    val HEIGHT_STATUS_BAR by lazy {
        resources.getDimensionPixelSize(
            resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
        )
    }

    fun getMenu(@MenuRes menuRes: Int): Menu? = PopupMenu(applicationContext, null).apply {
        MenuInflater(applicationContext).inflate(menuRes, menu)
    }.menu

}
