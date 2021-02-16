package androidx.essentials.io

import android.content.Context
import android.text.InputType.TYPE_DATETIME_VARIATION_TIME
import android.util.AttributeSet
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

    override var regex: Regex? = null
        set(_) {
            field = null
        }

    var time: Long? = null
        set(value) {
            field = value
            value?.let {
                today.timeInMillis = it
                materialTimePickerBuilder.apply {
                    setHour(today[Calendar.HOUR])
                    setMinute(today[Calendar.MINUTE])
                }
                editText.setText(displayTimeFormat.format(it))
            }
        }

    private var endTime: Long? = null
    private var startTime: Long? = null
    private val locale = Locale.getDefault()
    private val today = Calendar.getInstance()
    private val displayTimeFormat: SimpleDateFormat
    private lateinit var materialTimePicker: MaterialTimePicker

    private val materialTimePickerBuilder = MaterialTimePicker.Builder().apply {
        setTimeFormat(DEFAULT_TIME_FORMAT)
        setMinute(today[Calendar.MINUTE])
        setInputMode(DEFAULT_INPUT_MODE)
        setHour(today[Calendar.HOUR])
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Time, defStyleAttr, 0).apply {
            (getString(R.styleable.Time_android_format) ?: DEFAULT_FORMAT_DISPLAY_TIME).let {
                displayTimeFormat = SimpleDateFormat(it, locale)
            }
            materialTimePickerBuilder.setTitleText(hint)
            getResourceId(
                R.styleable.Time_calendarStyle,
                R.style.ThemeOverlay_MaterialComponents_TimePicker
            ).let { build().setStyle(DialogFragment.STYLE_NORMAL, it) }
            recycle()
        }
    }

    private fun build(): MaterialTimePicker {
        materialTimePicker = materialTimePickerBuilder.build()
        materialTimePicker.addOnPositiveButtonClickListener {
            val selectedTime = today.apply {
                this[Calendar.HOUR] = materialTimePicker.hour
                this[Calendar.MINUTE] = materialTimePicker.minute
            }.timeInMillis
            time = when {
                startTime != null && startTime!! > selectedTime -> startTime
                endTime != null && endTime!! < selectedTime -> endTime
                else -> selectedTime
            }
        }
        return materialTimePicker
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText.apply {
            keyListener = null
            isCursorVisible = false
            inputType = TYPE_DATETIME_VARIATION_TIME
            setOnFocusChangeListener { view, itHasFocus ->
                if (isEditable and itHasFocus) {
                    hideSoftInput(view)
                    view.clearFocus()
                    build().show()
                }
            }
        }
    }

    companion object {

        const val DEFAULT_TIME_FORMAT = TimeFormat.CLOCK_12H
        const val DEFAULT_FORMAT_DISPLAY_TIME = "h:mm a, d MMM" // "h:mm a"
        const val DEFAULT_INPUT_MODE = MaterialTimePicker.INPUT_MODE_CLOCK

        @JvmStatic
        @BindingAdapter("startTime")
        fun Time.setStartTime(time: Long?) {
            startTime = time
        }

        @JvmStatic
        @BindingAdapter("endTime")
        fun Time.setEndTime(time: Long?) {
            endTime = time
        }

        @JvmStatic
        @BindingAdapter("time")
        fun Time.setTime(time: Long?) = when (fromUser) {
            true -> fromUser = false
            false -> this.time = time
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "time")
        fun Time.getTime() = time

        @JvmStatic
        @BindingAdapter(value = ["timeAttrChanged"])
        fun Time.setOnTimeAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            editText.doAfterTextChanged {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }

}