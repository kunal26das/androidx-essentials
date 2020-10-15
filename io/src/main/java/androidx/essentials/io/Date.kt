package androidx.essentials.io

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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

    var date: Long? = null
        internal set(value) {
            field = value
            value?.let {
                editText?.setText(displayDateFormat.format(it))
                setOpenAt(value)
            }
        }

    private val locale = Locale.getDefault()
    private lateinit var materialDatePicker: MaterialDatePicker<Long>
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
                setOpenAt(null)
            }
            recycle()
        }
    }

    private fun setOpenAt(month: Long?) {
        month?.let { calendarConstraintsBuilder.setOpenAt(it) }
        materialDatePickerBuilder.setCalendarConstraints(calendarConstraintsBuilder.build())
        materialDatePickerBuilder.build()
        materialDatePicker = materialDatePickerBuilder.build()
        materialDatePicker.addOnPositiveButtonClickListener { date = it }
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
                        and !materialDatePicker.isAdded
                    ) {
                        with(context as AppCompatActivity) {
                            materialDatePicker.show(supportFragmentManager, null)
                        }
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

        @JvmStatic
        @BindingAdapter("date")
        fun Date.setDate(date: Long?) {
            when (fromUser) {
                true -> fromUser = false
                false -> this.date = date
            }
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "date")
        fun getDate(view: Date): Long? {
            return view.date
        }

        @JvmStatic
        @BindingAdapter(value = ["dateAttrChanged"])
        fun Date.setOnDateAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            editText?.doAfterTextChanged {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }

}