package androidx.essentials.backdrop

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.card.MaterialCardView

class BottomSheetView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null
) : MaterialCardView(context, attributes) {

    var peekHeight: Int = DEFAULT_PEEK_HEIGHT
        get() = bottomSheetBehaviour.peekHeight
        set(value) {
            try {
                field = value
                bottomSheetBehaviour.peekHeight = value
            } catch (e: UninitializedPropertyAccessException) {
            }
        }
    var isDraggable: Boolean = DEFAULT_IS_DRAGGABLE
        get() = bottomSheetBehaviour.isDraggable
        set(value) {
            try {
                field = value
                bottomSheetBehaviour.isDraggable = value
            } catch (e: UninitializedPropertyAccessException) {
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
            isDraggable = getBoolean(R.styleable.BottomSheetView_isDraggable, DEFAULT_IS_DRAGGABLE)
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initBottomSheetBehaviour(STATE_EXPANDED)
    }

    private fun initBottomSheetBehaviour(state: Int) {
        (layoutParams as CoordinatorLayout.LayoutParams).apply {
            bottomSheetBehaviour = BottomSheetBehavior()
            bottomSheetBehaviour.isDraggable = isDraggable
            bottomSheetBehaviour.state = state
            behavior = bottomSheetBehaviour
        }
    }

    fun hide() = try {
        bottomSheetBehaviour.state = STATE_HIDDEN
    } catch (e: RuntimeException) {
        initBottomSheetBehaviour(STATE_HIDDEN)
    }

    fun collapse() = try {
        bottomSheetBehaviour.state = STATE_COLLAPSED
    } catch (e: RuntimeException) {
        initBottomSheetBehaviour(STATE_COLLAPSED)
    }

    fun expand() = try {
        bottomSheetBehaviour.state = STATE_EXPANDED
    } catch (e: RuntimeException) {
        initBottomSheetBehaviour(STATE_EXPANDED)
    }

    fun halfExpand() = try {
        bottomSheetBehaviour.state = STATE_HALF_EXPANDED
    } catch (e: RuntimeException) {
        initBottomSheetBehaviour(STATE_HALF_EXPANDED)
    }

    fun switch() {
        when {
            isCollapsed -> expand()
            isExpanded -> collapse()
        }
    }

    companion object {
        private const val DEFAULT_PEEK_HEIGHT = 0
        private const val DEFAULT_IS_DRAGGABLE = false
    }
}