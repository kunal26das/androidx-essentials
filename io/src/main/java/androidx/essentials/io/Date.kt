package androidx.essentials.io

import android.content.Context
import android.os.Parcel
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
        set(value) {
            field = value
            value?.let {
                editText?.setText(displayDateFormat.format(it))
                setOpenDate(value)
            }
        }

    private val today
        get() = Calendar.getInstance().apply {
            this[Calendar.MILLISECOND] = 0
            this[Calendar.SECOND] = 0
            this[Calendar.MINUTE] = 0
            this[Calendar.HOUR_OF_DAY] = 0
        }.timeInMillis

    private val locale = Locale.getDefault()
    private lateinit var materialDatePicker: MaterialDatePicker<Long>
    private val calendarConstraintsBuilder = CalendarConstraints.Builder()
    private val displayDateFormat = SimpleDateFormat(DATE_FORMAT_DISPLAY, locale)

    private val materialDatePickerBuilder = MaterialDatePicker.Builder
        .datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)

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
                getResourceId(
                    R.styleable.Date_materialCalendarStyle,
                    R.style.ThemeOverlay_MaterialComponents_MaterialCalendar
                ).let { materialDatePickerBuilder.setTheme(it) }
            }
            recycle()
            build()
        }
    }

    fun setOpenDate(openDate: Long) {
        calendarConstraintsBuilder.setOpenAt(openDate)
        build()
    }

    fun setStartDate(startDate: Long) {
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
        build()
    }

    fun setEndDate(endDate: Long) {
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
        build()
    }

    private fun build() {
        materialDatePickerBuilder.apply {
            setCalendarConstraints(calendarConstraintsBuilder.build())
            materialDatePicker = build()
            materialDatePicker.addOnPositiveButtonClickListener { date = it }
        }
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
        @BindingAdapter("startDate")
        fun Date.setStartDate(date: Long?) {
            date?.let { setStartDate(it) }
        }

        @JvmStatic
        @BindingAdapter("endDate")
        fun Date.setEndDate(date: Long?) {
            date?.let { setEndDate(it) }
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