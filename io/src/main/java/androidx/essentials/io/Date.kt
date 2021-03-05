package androidx.essentials.io

import android.content.Context
import android.os.Parcel
import android.text.InputType.TYPE_DATETIME_VARIATION_DATE
import android.util.AttributeSet
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import java.text.SimpleDateFormat
import java.util.*

class Date @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textInputStyle
) : TextInput(context, attrs, defStyleAttr) {

    private val past: Boolean
    private val future: Boolean
    private var theme = DEFAULT_THEME

    var date: Long? = null
        set(value) {
            field = value?.apply {
                setOpenDate(this)
                editText.setText(displayDateFormat.format(this))
            }
            materialDatePickerBuilder.setSelection(value)
        }

    private val today = Calendar.getInstance().apply {
        this[Calendar.MILLISECOND] = 0
        this[Calendar.SECOND] = 0
        this[Calendar.MINUTE] = 0
        this[Calendar.HOUR_OF_DAY] = 0
    }.timeInMillis

    private val displayDateFormat: SimpleDateFormat
    private val calendarConstraintsBuilder = CalendarConstraints.Builder()
    private val materialDatePickerBuilder by lazy {
        MaterialDatePicker.Builder.datePicker().apply {
            setInputMode(INPUT_MODE_CALENDAR)
            setSelection(date)
            setTitleText(hint)
            setTheme(theme)
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.Date, defStyleAttr, 0).apply {
            (getString(R.styleable.Date_android_format) ?: DEFAULT_FORMAT_DISPLAY_DATE).let {
                displayDateFormat = SimpleDateFormat(it, locale)
            }
            future = getBoolean(R.styleable.Date_future, DEFAULT_FUTURE)
            past = getBoolean(R.styleable.Date_past, DEFAULT_PAST)
            calendarConstraintsBuilder.apply {
                when {
                    future and !past -> setValidator(DateValidatorPointForward.now())
                    !future and past -> setValidator(DateValidatorPointBackward.now())
                }
                theme = getResourceId(
                    R.styleable.Date_calendarStyle,
                    R.style.ThemeOverlay_MaterialComponents_MaterialCalendar
                )
            }
            inputType = TYPE_DATETIME_VARIATION_DATE
            recycle()
        }
        showSoftInputOnFocus = false
        doAfterTextChanged {
            if (it.isNullOrBlank()) {
                date = null
            }
        }
    }

    override fun show() {
        if (isEditable) {
            materialDatePickerBuilder.setCalendarConstraints(calendarConstraintsBuilder.build())
            materialDatePickerBuilder.build().apply {
                addOnPositiveButtonClickListener { date = it }
                addOnDismissListener { clearFocus() }
            }.show()
        } else hide()
    }

    companion object {

        const val DEFAULT_PAST = false
        const val DEFAULT_FUTURE = false
        const val DEFAULT_FORMAT_DISPLAY_DATE = "dd MMM yyyy"
        val DEFAULT_THEME = R.style.ThemeOverlay_MaterialComponents_MaterialCalendar

        @JvmStatic
        @BindingAdapter("openDate")
        fun Date.setOpenDate(openDate: Long?) {
            if (openDate != null) {
                calendarConstraintsBuilder.setOpenAt(openDate)
            }
        }

        @JvmStatic
        @BindingAdapter("startDate")
        fun Date.setStartDate(startDate: Long?) {
            if (startDate != null) {
                calendarConstraintsBuilder.setStart(startDate)
                calendarConstraintsBuilder.setValidator(object : CalendarConstraints.DateValidator {
                    override fun writeToParcel(dest: Parcel?, flags: Int) {
                        dest?.writeLong(startDate)
                    }

                    override fun isValid(date: Long) = when {
                        future and !past -> date >= today
                        !future and past -> date <= today
                        else -> true
                    } and (startDate <= date)

                    override fun describeContents() = 0
                })
            }
        }

        @JvmStatic
        @BindingAdapter("endDate")
        fun Date.setEndDate(endDate: Long?) {
            if (endDate != null) {
                calendarConstraintsBuilder.setEnd(endDate)
                calendarConstraintsBuilder.setValidator(object : CalendarConstraints.DateValidator {
                    override fun writeToParcel(dest: Parcel?, flags: Int) {
                        dest?.writeLong(endDate)
                    }

                    override fun isValid(date: Long) = when {
                        future and !past -> date >= today
                        !future and past -> date <= today
                        else -> true
                    } and (endDate >= date)

                    override fun describeContents() = 0
                })
            }
        }

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
        fun Date.getDate() = date

        @JvmStatic
        @BindingAdapter(value = ["dateAttrChanged"])
        fun Date.setOnDateAttrChangeListener(
            inverseBindingListener: InverseBindingListener
        ) {
            editText.doAfterTextChanged {
                fromUser = true
                inverseBindingListener.onChange()
            }
        }

    }

}