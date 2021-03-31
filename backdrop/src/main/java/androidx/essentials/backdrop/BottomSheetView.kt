package androidx.essentials.backdrop

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily

class BottomSheetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    @State
    private var state
        get() = bottomSheetBehaviour.state
        set(value) {
            bottomSheetBehaviour.state = value
        }

    var peekHeight
        get() = bottomSheetBehaviour.peekHeight
        set(value) {
            bottomSheetBehaviour.peekHeight = value
        }

    var isDraggable
        get() = bottomSheetBehaviour.isDraggable
        set(value) {
            bottomSheetBehaviour.isDraggable = value
        }

    var skipCollapsed
        get() = bottomSheetBehaviour.skipCollapsed
        set(value) {
            bottomSheetBehaviour.skipCollapsed = value
        }

    var isHideable
        get() = bottomSheetBehaviour.isHideable
        set(value) {
            bottomSheetBehaviour.isHideable = value
        }

    private val bottomSheetBehaviour = BottomSheetBehavior<View>()
    val isHidden get() = bottomSheetBehaviour.state == STATE_HIDDEN
    val isDragging get() = bottomSheetBehaviour.state == STATE_DRAGGING
    val isExpanded get() = bottomSheetBehaviour.state == STATE_EXPANDED
    val isSettling get() = bottomSheetBehaviour.state == STATE_SETTLING
    val isCollapsed get() = bottomSheetBehaviour.state == STATE_COLLAPSED
    val isHalfExpanded get() = bottomSheetBehaviour.state == STATE_HALF_EXPANDED

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BottomSheetView, defStyleAttr, 0).apply {
            peekHeight =
                getDimensionPixelSize(R.styleable.BottomSheetView_peekHeight, DEFAULT_PEEK_HEIGHT)
            isDraggable = getBoolean(R.styleable.BottomSheetView_draggable, DEFAULT_IS_DRAGGABLE)
            isHideable = getBoolean(R.styleable.BottomSheetView_hideable, DEFAULT_IS_HIDEABLE)
            skipCollapsed =
                getBoolean(R.styleable.BottomSheetView_skipCollapsed, DEFAULT_SKIP_COLLAPSED)
            state = getInteger(R.styleable.BottomSheetView_state, STATE_EXPANDED)
            recycle()
        }
        shapeAppearanceModel = shapeAppearanceModel.toBuilder().apply {
            setTopLeftCorner(CornerFamily.ROUNDED, radius)
            setTopRightCorner(CornerFamily.ROUNDED, radius)
            radius = 0f
            setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            setBottomRightCorner(CornerFamily.ROUNDED, radius)
        }.build()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            with(layoutParams as CoordinatorLayout.LayoutParams) {
                behavior = bottomSheetBehaviour
            }
        }
    }

    fun collapse() {
        state = STATE_COLLAPSED
    }

    fun expand(half: Boolean = false) {
        state = when {
            half -> STATE_HALF_EXPANDED
            else -> STATE_EXPANDED
        }
    }

    fun hide() {
        state = STATE_HIDDEN
    }

    companion object {
        private const val DEFAULT_IS_HIDEABLE = false
        private const val DEFAULT_IS_DRAGGABLE = false
        private const val DEFAULT_SKIP_COLLAPSED = false
        private const val DEFAULT_PEEK_HEIGHT = PEEK_HEIGHT_AUTO
    }
}
