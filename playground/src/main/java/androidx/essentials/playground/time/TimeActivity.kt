package androidx.essentials.playground.time

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
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TimeActivity : ComposeActivity() {

    private val viewModel by viewModels<TimeViewModel>()

    private val simpleTimeFormat by lazy {
        SimpleDateFormat("h:mm a", Locale.getDefault())
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
                title = { Text(text = Feature.Time.name) }
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
        val startTime by viewModel.startTime.observeAsState()
        TextField(
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) startTime.getMaterialTimePicker {
                    viewModel.startTime.value = it
                }.showNow()
            },
            label = { Text(text = getString(R.string.start_time)) },
            value = if (startTime != null) simpleTimeFormat.format(startTime) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun EndDateTextField() {
        val endTime by viewModel.endTime.observeAsState()
        TextField(
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) endTime.getMaterialTimePicker {
                    viewModel.endTime.value = it
                }.showNow()
            },
            label = { Text(text = getString(R.string.end_time)) },
            value = if (endTime != null) simpleTimeFormat.format(endTime) else "",
            onValueChange = {},
            readOnly = true,
        )
    }

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
