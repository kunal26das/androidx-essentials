package androidx.essentials.core.utils

import android.content.res.Resources
import android.util.TypedValue
import androidx.essentials.core.injector.KoinComponent.inject
import kotlin.math.roundToInt

object Resources {

    private val resources: Resources by inject()

    val Int.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), resources.displayMetrics
        ).roundToInt()

    val Float.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), resources.displayMetrics
        )

    val statusBarHeight = resources.getDimensionPixelSize(
        resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
    )
}
