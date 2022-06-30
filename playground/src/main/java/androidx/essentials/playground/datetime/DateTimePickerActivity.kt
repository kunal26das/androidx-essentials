package androidx.essentials.playground.datetime

import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.Feature
import androidx.essentials.playground.R
import androidx.essentials.view.ComposeActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DateTimePickerActivity : ComposeActivity() {

    private val viewModel by viewModels<DateTimePickerViewModel>()

    private val locale by lazy { Locale.getDefault() }
    private val timeFormat by lazy { SimpleDateFormat("h:mm a", locale) }
    private val dateFormat by lazy { SimpleDateFormat("dd MMM yyyy", locale) }
    private val dateTimeFormat by lazy { SimpleDateFormat("dd MMM yyyy, h:mm a", locale) }

    private val modifier
        get() = Modifier
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            )
            .fillMaxWidth()

    @Composable
    override fun setContent() {
        super.setContent()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
        ) {
            LargeTopAppBar(
                title = { Text(text = Feature.DateTimePicker.name) }
            )
            StartDateTextField()
            StartTimeTextField()
            StartDateTimeText()
            EndDateTextField()
            EndTimeTextField()
            EndDateTimeText()
        }
    }

    @Composable
    private fun StartDateTextField() {
        val endDate by viewModel.endDate.observeAsState()
        val startDate by viewModel.startDate.observeAsState()
        TextField(
            modifier = modifier.onFocusChanged {
                if (it.isFocused) startDate
                    .getDatePicker {
                        setValidator(DateValidator(endDate = endDate))
                        if (endDate != null) setEnd(endDate!!)
                    }
                    .apply {
                        addOnPositiveButtonClickListener {
                            viewModel.startDate.value = it
                        }
                    }
                    .showNow()
            },
            label = { Text(text = getString(R.string.start_date)) },
            value = if (startDate != null) dateFormat.format(startDate) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun EndDateTextField() {
        val endDate by viewModel.endDate.observeAsState()
        val startDate by viewModel.startDate.observeAsState()
        TextField(
            modifier = modifier.onFocusChanged {
                if (it.isFocused) endDate
                    .getDatePicker {
                        setValidator(DateValidator(startDate = startDate))
                        if (startDate != null) setStart(startDate!!)
                    }
                    .apply {
                        addOnPositiveButtonClickListener {
                            viewModel.endDate.value = it
                        }
                    }
                    .showNow()
            },
            label = { Text(text = getString(R.string.end_date)) },
            value = if (endDate != null) dateFormat.format(endDate) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun StartDateTimeText() {
        val startDate by viewModel.startDate.observeAsState()
        val startTime by viewModel.startTime.observeAsState()
        startDate?.plus(startTime ?: 0)?.let {
            TextField(
                modifier = modifier,
                value = dateTimeFormat.format(it),
                onValueChange = {},
                readOnly = true,
            )
        }
    }

    @Composable
    private fun StartTimeTextField() {
        val startTime by viewModel.startTime.observeAsState()
        TextField(
            modifier = modifier.onFocusChanged {
                if (it.isFocused) startTime
                    .getMaterialTimePicker {
                        viewModel.startTime.value = it
                    }
                    .showNow()
            },
            label = { Text(text = getString(R.string.start_time)) },
            value = if (startTime != null) timeFormat.format(startTime) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun EndTimeTextField() {
        val endTime by viewModel.endTime.observeAsState()
        TextField(
            modifier = modifier.onFocusChanged {
                if (it.isFocused) endTime
                    .getMaterialTimePicker {
                        viewModel.endTime.value = it
                    }
                    .showNow()
            },
            label = { Text(text = getString(R.string.end_time)) },
            value = if (endTime != null) timeFormat.format(endTime) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun EndDateTimeText() {
        val endDate by viewModel.endDate.observeAsState()
        val endTime by viewModel.endTime.observeAsState()
        endDate?.plus(endTime ?: 0)?.let {
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = dateTimeFormat.format(it),
                onValueChange = {},
                readOnly = true,
            )
        }
    }

    private fun Long?.getDatePicker(
        builder: (CalendarConstraints.Builder.() -> Unit)? = null,
    ) = MaterialDatePicker.Builder.datePicker().apply {
        setCalendarConstraints(CalendarConstraints.Builder().apply {
            setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            setSelection(this@getDatePicker)
            builder?.invoke(this)
        }.build())
    }.build()

    private fun Long?.getMaterialTimePicker(
        onPositiveButtonClickListener: ((Long) -> Unit)? = null,
    ): MaterialTimePicker {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this ?: 0L
        return MaterialTimePicker.Builder().apply {
            setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            setTimeFormat(TimeFormat.CLOCK_12H)
            setMinute(calendar[Calendar.MINUTE])
            setHour(calendar[Calendar.HOUR])
        }.build().apply {
            addOnPositiveButtonClickListener {
                calendar[Calendar.HOUR] = hour
                calendar[Calendar.MINUTE] = minute
                onPositiveButtonClickListener?.invoke(calendar.timeInMillis)
            }
        }
    }

}
