package androidx.essentials.playground.date

import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
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
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DateActivity : ComposeActivity() {

    private val viewModel by viewModels<DateViewModel>()

    private val simpleDateFormat by lazy {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    @Composable
    override fun setContent() {
        super.setContent()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
        ) {
            LargeTopAppBar(
                title = { Text(text = Feature.Date.name) }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    StartDateTextField()
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    EndDateTextField()
                }
            }
        }
    }

    @Composable
    private fun StartDateTextField() {
        val endDate by viewModel.endDate.observeAsState()
        val startDate by viewModel.startDate.observeAsState()
        TextField(
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) startDate.getDatePicker {
                    setValidator(DateValidator(endDate = endDate))
                    if (endDate != null) setEnd(endDate!!)
                }.apply {
                    addOnPositiveButtonClickListener {
                        viewModel.startDate.value = it
                    }
                }.showNow()
            },
            label = { Text(text = getString(R.string.start_date)) },
            value = if (startDate != null) simpleDateFormat.format(startDate) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun EndDateTextField() {
        val endDate by viewModel.endDate.observeAsState()
        val startDate by viewModel.startDate.observeAsState()
        TextField(
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) endDate.getDatePicker {
                    setValidator(DateValidator(startDate = startDate))
                    if (startDate != null) setStart(startDate!!)
                }.apply {
                    addOnPositiveButtonClickListener {
                        viewModel.endDate.value = it
                    }
                }.showNow()
            },
            label = { Text(text = getString(R.string.end_date)) },
            value = if (endDate != null) simpleDateFormat.format(endDate) else "",
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

}
