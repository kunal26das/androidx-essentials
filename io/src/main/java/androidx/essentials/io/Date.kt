package androidx.essentials.io

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class Date @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInput(context, attrs, defStyleAttr) {

    private val past: Boolean
    private val future: Boolean

    override var inputType = DEFAULT_INPUT_TYPE
        set(_) {
            field = DEFAULT_INPUT_TYPE
        }

    override var regex: Regex? = null
        set(_) {
            field = null
        }

    private val locale = Locale.getDefault()
    private val materialDatePicker: MaterialDatePicker<Long>
    private val calendarConstraintsBuilder = CalendarConstraints.Builder()
    private val displayDateFormat = SimpleDateFormat(DATE_FORMAT_DISPLAY, locale)
    private val materialDatePickerBuilder = MaterialDatePicker.Builder.datePicker().apply {
        setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
        setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Date, defStyleAttr, 0).apply {
            future = getBoolean(R.styleable.Date_future, DEFAULT_FUTURE)
            past = getBoolean(R.styleable.Date_past, DEFAULT_PAST)
            calendarConstraintsBuilder.apply {
                when {
                    future and !past -> setValidator(DateValidatorPointForward.now())
                    !future and past -> setValidator(DateValidatorPointBackward.now())
                }
                materialDatePickerBuilder.setTitleText(hint)
                materialDatePickerBuilder.setCalendarConstraints(build())
                materialDatePicker = materialDatePickerBuilder.build()
                materialDatePicker.addOnPositiveButtonClickListener {
                    editText?.setText(displayDateFormat.format(it))
                }
            }
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText?.apply {
            setOnFocusChangeListener { view, itHasFocus ->
                view.clearFocus()
                hideKeyboard(view)
                if (isEditable and itHasFocus) {
                    try {
                        materialDatePicker.show(
                            (context as AppCompatActivity).supportFragmentManager,
                            null
                        )
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    companion object {
        const val DEFAULT_PAST = false
        const val DEFAULT_FUTURE = false
        const val DATE_FORMAT_DISPLAY = "dd MMM yyyy"
        const val DEFAULT_INPUT_TYPE = InputType.TYPE_DATETIME_VARIATION_DATE
    }

}