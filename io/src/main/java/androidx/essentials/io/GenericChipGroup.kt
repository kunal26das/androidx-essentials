package androidx.essentials.io

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.animation.ScaleAnimation
import android.widget.Checkable
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

abstract class GenericChipGroup<T> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipGroupStyle
) : ChipGroup(context, attrs, defStyleAttr) {

    private val chipLayout: Int
    internal var fromUser = false
    val isInvalid get() = !isValid
    private val inflater = LayoutInflater.from(context)
    private var onChipClickListener: OnChipClickListener<T>? = null

    var isCheckable = DEFAULT_IS_CHECKABLE
        set(value) {
            field = value
            repeat(childCount) {
                getChildAt(it)?.apply {
                    isChecked = isChecked and value
                    isCheckable = value
                }
            }
        }

    val isValid
        get() = when (isSelectionRequired) {
            true -> when (isCheckable) {
                true -> when (isVisible) {
                    true -> !selection.isNullOrEmpty()
                    false -> true
                }
                false -> true
            }
            false -> true
        }

    var chips: Set<T>? = null
        set(value) {
            field = value?.apply {
                removeAllViews().apply {
                    forEachIndexed { index, it ->
                        (inflater.inflate(chipLayout, this@GenericChipGroup, false) as Chip).apply {
                            startAnimation(ScaleAnimation(0f, 1f, 1f, 1f))
                            isCheckable = this@GenericChipGroup.isCheckable
                            isChecked = when (it) {
                                is String -> selection?.contains(it) ?: false
                                is Checkable -> it.isChecked
                                is MenuItem -> it.isChecked
                                else -> false
                            }
                            text = it.toString()
                            addView(this)
                            setOnCheckedChangeListener { _, isChecked ->
                                if (isCheckable) {
                                    when (it) {
                                        is Checkable -> it.isChecked = isChecked
                                        is MenuItem -> it.isChecked = isChecked
                                    }
                                    onChipClickListener?.onChipClick(index, it, isChecked)
                                }
                            }
                        }
                    }
                }
            }
        }

    open val selection: Set<T>?
        get() = chips?.filterIndexed { index, it ->
            when (it) {
                is MenuItem -> it.isChecked
                is Checkable -> it.isChecked
                else -> getChildAt(index)?.isChecked == true
            }
        }?.toSet()

    open val inverseSelection: Set<T>?
        get() = chips?.filterIndexed { index, it ->
            when (it) {
                is MenuItem -> !it.isChecked
                is Checkable -> !it.isChecked
                else -> getChildAt(index)?.isChecked == false
            }
        }?.toSet()

    init {
        context.obtainStyledAttributes(attrs, R.styleable.GenericChipGroup, defStyleAttr, 0).apply {
            isCheckable =
                getBoolean(R.styleable.GenericChipGroup_android_checkable, DEFAULT_IS_CHECKABLE)
            chipLayout = getResourceId(R.styleable.GenericChipGroup_chip, DEFAULT_CHIP_LAYOUT)
            recycle()
        }
    }

    override fun getChildAt(index: Int): Chip? {
        return super.getChildAt(index) as? Chip
    }

    fun setOnChipClickListener(onChipClickListener: OnChipClickListener<T>) {
        this.onChipClickListener = onChipClickListener
    }

    fun interface OnChipClickListener<T> {
        fun onChipClick(index: Int, item: T, isChecked: Boolean)
    }

    companion object {

        const val DEFAULT_IS_CHECKABLE = true
        val DEFAULT_CHIP_LAYOUT = R.layout.chip_filter

        @JvmStatic
        @BindingAdapter("chips")
        fun <T> GenericChipGroup<T>.setChips(chips: Set<T>?) {
            when (fromUser) {
                true -> fromUser = false
                false -> this.chips = chips
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "chips")
        fun <T> GenericChipGroup<T>.getChips() = chips

        @JvmStatic
        @BindingAdapter(value = ["chipsAttrChanged"])
        fun <T> GenericChipGroup<T>.setOnChipsAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) = setOnChipClickListener { _, _, _ ->
            fromUser = true
            inverseBindingListener.onChange()
        }

    }

}