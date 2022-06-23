package androidx.essentials.playground.preferences

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesActivity : ComposeActivity() {

    private val viewModel by viewModels<PreferencesViewModel>()

    @Composable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            IntTextField()
            LongTextField()
            FloatTextField()
            StringTextField()
            BooleanSwitch()
        }
    }

    @Composable
    private fun IntTextField() {
        val int by viewModel.int.observeAsState()
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = PreferencesViewModel.KEY_INT) },
            value = int?.toString() ?: "",
            onValueChange = {
                viewModel.int.value = it.toInt()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }

    @Composable
    private fun LongTextField() {
        val long by viewModel.long.observeAsState()
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = PreferencesViewModel.KEY_LONG) },
            value = long?.toString() ?: "",
            onValueChange = {
                viewModel.long.value = it.toLong()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }

    @Composable
    private fun FloatTextField() {
        val float by viewModel.float.observeAsState()
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = PreferencesViewModel.KEY_FLOAT) },
            value = float?.toString() ?: "",
            onValueChange = {
                viewModel.float.value = it.toFloat()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }

    @Composable
    private fun StringTextField() {
        val string by viewModel.string.observeAsState()
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = PreferencesViewModel.KEY_STRING) },
            value = string ?: "",
            onValueChange = {
                viewModel.string.value = it
            },
        )
    }

    @Composable
    private fun BooleanSwitch() {
        val boolean by viewModel.boolean.observeAsState()
        Switch(
            modifier = Modifier.padding(8.dp),
            checked = boolean ?: false,
            onCheckedChange = {
                viewModel.boolean.value = it
            },
        )
    }

}