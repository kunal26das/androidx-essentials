package androidx.essentials.io

import android.content.Context
import android.text.InputType.TYPE_DATETIME_VARIATION_TIME
import android.util.AttributeSet
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

    var time: Long? = null
        set(value) {
            field = value
            when (value) {
                null -> Unit
                else -> {
                    today.timeInMillis = value
                    materialTimePickerBuilder.apply {
                        setHour(today[Calendar.HOUR])
                        setMinute(today[Calendar.MINUTE])
                    }
                    setText(displayTimeFormat.format(value))
                }
            }
            onTimeChangeListener?.onTimeChange(value)
        }

    private val style: Int
    private var endTime: Long? = null
    private var startTime: Long? = null
    private val today = Calendar.getInstance()
    private val displayTimeFormat: SimpleDateFormat
    private var onTimeChangeListener: OnTimeChangeListener? = null

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
            inputType = TYPE_DATETIME_VARIATION_TIME
            style = getResourceId(
                R.styleable.Time_calendarStyle,
                R.style.ThemeOverlay_MaterialComponents_TimePicker
            )
            recycle()
        }
        showSoftInputOnFocus = false
        doAfterTextChanged {
            if (it.isNullOrEmpty()) {
                time = null
            }
        }
    }

    override fun show() {
        if (isEditable) {
            materialTimePickerBuilder.build().apply {
                setStyle(DialogFragment.STYLE_NORMAL, style)
                addOnDismissListener { clearFocus() }
                addOnPositiveButtonClickListener {
                    val selectedTime = today.apply {
                        this[Calendar.HOUR] = hour
                        this[Calendar.MINUTE] = minute
                    }.timeInMillis
                    time = when {
                        startTime != null && startTime!! > selectedTime -> startTime
                        endTime != null && endTime!! < selectedTime -> endTime
                        else -> selectedTime
                    }
                }
            }.show()
        } else hide()
    }

    fun setOnTimeChangeListener(onTimeChangeListener: OnTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener
    }

    fun setOnTimeChangeListener(onTimeChange: (Long?) -> Unit) {
        setOnTimeChangeListener(object : OnTimeChangeListener {
            override fun onTimeChange(time: Long?) {
                onTimeChange.invoke(time)
            }
        })
    }

    interface OnTimeChangeListener {
        fun onTimeChange(time: Long?)
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
        fun Time.setTime(time: Long?) {
            this.time = time
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "time")
        fun Time.getTime() = time

        @JvmStatic
        @BindingAdapter(value = ["timeAttrChanged"])
        fun Time.setOnTimeAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            setOnTimeChangeListener {
                inverseBindingListener.onChange()
            }
        }

    }

}