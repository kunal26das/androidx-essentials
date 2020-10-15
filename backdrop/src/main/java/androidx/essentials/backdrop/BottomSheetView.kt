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
    attributes: AttributeSet? = null,
    styleAttributes: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attributes, styleAttributes) {

    private var state = STATE_EXPANDED
        set(value) {
            field = value
            Try {
                bottomSheetBehaviour.state = value
            }
        }

    var peekHeight = DEFAULT_PEEK_HEIGHT
        set(value) {
            field = value
            Try {
                bottomSheetBehaviour.peekHeight = value
            }
        }

    var isDraggable = DEFAULT_IS_DRAGGABLE
        set(value) {
            field = value
            Try {
                bottomSheetBehaviour.isDraggable = value
            }
        }

    var skipCollapsed = false
        set(value) {
            field = value
            Try {
                bottomSheetBehaviour.skipCollapsed = value
            }
        }

    var isHideable = false
        set(value) {
            field = value
            Try {
                bottomSheetBehaviour.isHideable = value
            }
        }

    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<View>
    val isHidden get() = bottomSheetBehaviour.state == STATE_HIDDEN
    val isDragging get() = bottomSheetBehaviour.state == STATE_DRAGGING
    val isExpanded get() = bottomSheetBehaviour.state == STATE_EXPANDED
    val isSettling get() = bottomSheetBehaviour.state == STATE_SETTLING
    val isCollapsed get() = bottomSheetBehaviour.state == STATE_COLLAPSED
    val isHalfExpanded get() = bottomSheetBehaviour.state == STATE_HALF_EXPANDED

    init {
        context.obtainStyledAttributes(attributes, R.styleable.BottomSheetView, 0, 0).apply {
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
        initBottomSheetBehaviour()
    }

    private fun initBottomSheetBehaviour() {
        (layoutParams as CoordinatorLayout.LayoutParams).apply {
            bottomSheetBehaviour = BottomSheetBehavior()
            bottomSheetBehaviour.skipCollapsed = skipCollapsed
            bottomSheetBehaviour.isDraggable = isDraggable
            bottomSheetBehaviour.isHideable = isHideable
            bottomSheetBehaviour.peekHeight = peekHeight
            bottomSheetBehaviour.state = state
            behavior = bottomSheetBehaviour
        }
    }

    fun hide() {
        state = STATE_HIDDEN
    }

    fun collapse() {
        state = STATE_COLLAPSED
    }

    fun expand() {
        state = STATE_EXPANDED
    }

    fun halfExpand() {
        state = STATE_HALF_EXPANDED
    }

    fun switch() {
        when {
            isCollapsed -> expand()
            isExpanded -> collapse()
        }
    }

    inline fun <T> T.Try(
        block: T.() -> Unit
    ): T {
        try {
            block()
        } catch (e: Exception) {
        }
        return this
    }

    companion object {
        private const val DEFAULT_IS_HIDEABLE = false
        private const val DEFAULT_IS_DRAGGABLE = false
        private const val DEFAULT_SKIP_COLLAPSED = false
        private const val DEFAULT_PEEK_HEIGHT = PEEK_HEIGHT_AUTO
    }
}
