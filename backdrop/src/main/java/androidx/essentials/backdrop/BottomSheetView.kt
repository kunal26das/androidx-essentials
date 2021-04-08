package androidx.essentials.backdrop

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
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

    var expandedOffset
        get() = bottomSheetBehaviour.expandedOffset
        set(value) {
            bottomSheetBehaviour.expandedOffset = value
        }

    @FloatRange(from = 0.0, to = 1.0)
    var halfExpandedRatio = DEFAULT_HALF_EXPANDED_RATIO
        get() = bottomSheetBehaviour.halfExpandedRatio
        set(value) {
            field = value
            bottomSheetBehaviour.halfExpandedRatio = value
        }

    var isDraggable
        get() = bottomSheetBehaviour.isDraggable
        set(value) {
            bottomSheetBehaviour.isDraggable = value
        }

    var isFitToContents
        get() = bottomSheetBehaviour.isFitToContents
        set(value) {
            bottomSheetBehaviour.isFitToContents = value
        }

    var isGestureInsetBottomIgnored
        get() = bottomSheetBehaviour.isGestureInsetBottomIgnored
        set(value) {
            bottomSheetBehaviour.isGestureInsetBottomIgnored = value
        }

    var isHideable
        get() = bottomSheetBehaviour.isHideable
        set(value) {
            bottomSheetBehaviour.isHideable = value
        }

    var peekHeight
        get() = bottomSheetBehaviour.peekHeight
        set(value) {
            bottomSheetBehaviour.peekHeight = value
        }

    var skipCollapsed
        get() = bottomSheetBehaviour.skipCollapsed
        set(value) {
            bottomSheetBehaviour.skipCollapsed = value
        }

    var updateImportantForAccessibilityOnSiblings =
        DEFAULT_UPDATE_IMPORTANT_FOR_ACCESSIBILITY_ON_SIBLINGS
        set(value) {
            field = value
            bottomSheetBehaviour.setUpdateImportantForAccessibilityOnSiblings(value)
        }

    @State
    private var state
        get() = bottomSheetBehaviour.state
        set(value) {
            bottomSheetBehaviour.state = value
        }
    val isHidden get() = state == STATE_HIDDEN
    val isDragging get() = state == STATE_DRAGGING
    val isExpanded get() = state == STATE_EXPANDED
    val isSettling get() = state == STATE_SETTLING
    val isCollapsed get() = state == STATE_COLLAPSED
    val isHalfExpanded get() = state == STATE_HALF_EXPANDED

    private var slideListener: SlideListener? = null
    private var stateChangeListener: StateChangeListener? = null
    private val bottomSheetBehaviour = BottomSheetBehavior<View>()
    private val bottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, @State newState: Int) {
            stateChangeListener?.onStateChange(newState)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            slideListener?.onSlide(slideOffset)
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BottomSheetView, defStyleAttr, 0).apply {
            expandedOffset =
                getInt(R.styleable.BottomSheetView_expandedOffset, DEFAULT_EXPANDED_OFFSET)
            halfExpandedRatio =
                getFloat(R.styleable.BottomSheetView_halfExpandedRatio, DEFAULT_HALF_EXPANDED_RATIO)
            isDraggable = getBoolean(R.styleable.BottomSheetView_draggable, DEFAULT_IS_DRAGGABLE)
            isFitToContents =
                getBoolean(R.styleable.BottomSheetView_fitToContents, DEFAULT_IS_FIT_TO_CONTENTS)
            isGestureInsetBottomIgnored = getBoolean(
                R.styleable.BottomSheetView_gestureInsetBottomIgnored,
                DEFAULT_IS_GESTURE_INSET_BOTTOM_IGNORED
            )
            isHideable = getBoolean(R.styleable.BottomSheetView_hideable, DEFAULT_IS_HIDEABLE)
            peekHeight =
                getDimensionPixelSize(R.styleable.BottomSheetView_peekHeight, DEFAULT_PEEK_HEIGHT)
            skipCollapsed =
                getBoolean(R.styleable.BottomSheetView_skipCollapsed, DEFAULT_SKIP_COLLAPSED)
            state = getInteger(R.styleable.BottomSheetView_state, STATE_EXPANDED)
            updateImportantForAccessibilityOnSiblings = getBoolean(
                R.styleable.BottomSheetView_updateImportantForAccessibilityOnSiblings,
                DEFAULT_UPDATE_IMPORTANT_FOR_ACCESSIBILITY_ON_SIBLINGS
            )
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
                behavior = bottomSheetBehaviour.apply {
                    addBottomSheetCallback(bottomSheetCallback)
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            bottomSheetBehaviour.removeBottomSheetCallback(bottomSheetCallback)
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

    fun setOnStateChangeListener(stateChangeListener: StateChangeListener) {
        this.stateChangeListener = stateChangeListener
    }

    fun setOnSlideListener(slideListener: SlideListener) {
        this.slideListener = slideListener
    }

    fun interface StateChangeListener {
        fun onStateChange(@State newState: Int)
    }

    fun interface SlideListener {
        fun onSlide(slideOffset: Float)
    }

    companion object {
        const val DEFAULT_EXPANDED_OFFSET = 0
        const val DEFAULT_IS_HIDEABLE = false
        const val DEFAULT_IS_DRAGGABLE = true
        const val DEFAULT_SKIP_COLLAPSED = false
        const val DEFAULT_IS_FIT_TO_CONTENTS = true
        const val DEFAULT_HALF_EXPANDED_RATIO = 0.5f
        const val DEFAULT_PEEK_HEIGHT = PEEK_HEIGHT_AUTO
        const val DEFAULT_IS_GESTURE_INSET_BOTTOM_IGNORED = false
        const val DEFAULT_UPDATE_IMPORTANT_FOR_ACCESSIBILITY_ON_SIBLINGS = false
    }
}
