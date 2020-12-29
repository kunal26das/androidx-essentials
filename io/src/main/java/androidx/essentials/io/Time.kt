package androidx.essentials.io

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class Time @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInput(context, attrs, defStyleAttr) {

    override var inputType = DEFAULT_INPUT_TYPE
        set(_) {
            field = DEFAULT_INPUT_TYPE
        }

    override var regex: Regex? = null
        set(_) {
            field = null
        }

    var time: Long? = null
        set(value) {
            field = value?.apply {
                editText?.setText(displayTimeFormat.format(this))
            }
        }

    private val today
        get() = Calendar.getInstance().apply {
            this[Calendar.MILLISECOND] = 0
            this[Calendar.SECOND] = 0
            this[Calendar.MINUTE] = 0
            this[Calendar.HOUR_OF_DAY] = 0
        }

    private val locale = Locale.getDefault()
    private lateinit var materialTimePicker: MaterialTimePicker
    private val displayTimeFormat = SimpleDateFormat(TIME_FORMAT_DISPLAY, locale)

    private val materialTimePickerBuilder = MaterialTimePicker.Builder()
        .setTimeFormat(DEFAULT_TIME_FORMAT)
        .setInputMode(DEFAULT_INPUT_MODE)
        .setMinute(DEFAULT_MINUTE)
        .setHour(DEFAULT_HOUR)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Time, defStyleAttr, 0).apply {
            materialTimePickerBuilder.setTitleText(hint)
            getResourceId(
                R.styleable.Time_materialCalendarStyle,
                R.style.Widget_MaterialComponents_TimePicker
            ).let { build().setStyle(DialogFragment.STYLE_NORMAL, it) }
            recycle()
        }
    }

    private fun build(): MaterialTimePicker {
        materialTimePicker = materialTimePickerBuilder.build()
        materialTimePicker.addOnPositiveButtonClickListener {
            time = today.apply {
                this[Calendar.HOUR] = materialTimePicker.hour
                this[Calendar.MINUTE] = materialTimePicker.minute
            }.timeInMillis
        }
        return materialTimePicker
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText?.apply {
            keyListener = null
            isCursorVisible = false
            setOnFocusChangeListener { view, itHasFocus ->
                if (isEditable and itHasFocus) {
                    view.clearFocus()
                    hideSoftInput(view)
                    if ((context is AppCompatActivity)
                        and !materialTimePicker.isAdded
                    ) {
                        with(context as AppCompatActivity) {
                            build().show(supportFragmentManager, null)
                        }
                    }
                }
            }
        }
    }

    companion object {

        const val DEFAULT_HOUR = 0
        const val DEFAULT_MINUTE = 0
        const val TIME_FORMAT_DISPLAY = "h:mm a, d MMM"
        const val DEFAULT_TIME_FORMAT = TimeFormat.CLOCK_12H
        const val DEFAULT_INPUT_MODE = MaterialTimePicker.INPUT_MODE_CLOCK
        const val DEFAULT_INPUT_TYPE = InputType.TYPE_DATETIME_VARIATION_DATE

        @JvmStatic
        @BindingAdapter("time")
        fun Time.setTime(time: Long?) {
            when (fromUser) {
                true -> fromUser = false
                false -> this.time = time
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "time")
        fun Time.getTime(): Long? {
            return time
        }

        @JvmStatic
        @BindingAdapter(value = ["timeAttrChanged"])
        fun Time.setOnTimeAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            editText?.doAfterTextChanged {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }

}