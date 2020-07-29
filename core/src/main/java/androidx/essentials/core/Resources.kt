package androidx.essentials.core

import android.content.res.Resources
import android.util.TypedValue
import androidx.essentials.core.KoinComponent.inject
import kotlin.math.roundToInt

object Resources {

    private val resources: Resources by inject()

    val Int.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), resources.displayMetrics
        ).roundToInt()

    val statusBarHeight = resources.getDimensionPixelSize(
        resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
    )
}
