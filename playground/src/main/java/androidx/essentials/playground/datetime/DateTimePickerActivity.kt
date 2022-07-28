package androidx.essentials.playground.datetime

import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
@OptIn(ExperimentalMaterial3Api::class)
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
    override fun Content() {
        super.Content()
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
    private fun StartTimeTextField() {
        val startDate by viewModel.startDate.observeAsState()
        TextField(
            modifier = modifier.onFocusChanged {
                if (it.isFocused) startDate
                    .getMaterialTimePicker {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = startDate ?: 0L
                        calendar[Calendar.HOUR] = it.first
                        calendar[Calendar.MINUTE] = it.second
                        viewModel.startDate.value = calendar.timeInMillis
                    }
                    .showNow()
            },
            label = { Text(text = getString(R.string.start_time)) },
            value = if (startDate != null) timeFormat.format(startDate) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun StartDateTimeText() {
        val startDate by viewModel.startDate.observeAsState()
        TextField(
            modifier = modifier,
            value = if (startDate != null) dateTimeFormat.format(startDate) else "",
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
    private fun EndTimeTextField() {
        val endDate by viewModel.endDate.observeAsState()
        TextField(
            modifier = modifier.onFocusChanged {
                if (it.isFocused) endDate
                    .getMaterialTimePicker {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = endDate ?: 0L
                        calendar[Calendar.HOUR] = it.first
                        calendar[Calendar.MINUTE] = it.second
                        viewModel.endDate.value = calendar.timeInMillis
                    }
                    .showNow()
            },
            label = { Text(text = getString(R.string.end_time)) },
            value = if (endDate != null) timeFormat.format(endDate) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun EndDateTimeText() {
        val endDate by viewModel.endDate.observeAsState()
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = if (endDate != null) dateTimeFormat.format(endDate) else "",
            onValueChange = {},
            readOnly = true,
        )
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
        onPositiveButtonClickListener: ((Pair<Int, Int>) -> Unit)? = null,
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
                onPositiveButtonClickListener?.invoke(hour to minute)
            }
        }
    }

}
