package androidx.essentials.resources

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ExtendedFloatingActionButton(
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout.Behavior<ExtendedFloatingActionButton>() {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ExtendedFloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) = true

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ExtendedFloatingActionButton,
        dependency: View
    ) = when (dependency) {
        is RecyclerView -> true
        is NestedScrollView -> true
        else -> false
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ExtendedFloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
        if (dyConsumed > 0 && child.isExtended) {
            child.shrink()
        } else if (dyConsumed < 0 && !child.isExtended) {
            child.extend()
        }
    }

}