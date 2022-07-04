package androidx.essentials.playground.datastore

import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.Constant
import androidx.essentials.playground.Feature
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DataStoreActivity : ComposeActivity(), Constant {

    private val viewModel by viewModels<DataStoreViewModel>()

    private val modifier
        get() = Modifier
            .padding(8.dp)
            .fillMaxWidth()

    @Composable
    override fun setContent() {
        super.setContent()
        Column(modifier.verticalScroll(ScrollState(0))) {
            LargeTopAppBar(
                title = { Text(text = Feature.DataStore.name) }
            )
            IntTextField()
            LongTextField()
            FloatTextField()
            DoubleTextField()
            StringTextField()
            BooleanSwitch()
        }
    }

    @Composable
    private fun IntTextField() {
        val int by viewModel.int.observeAsState()
        val boolean by viewModel.boolean.observeAsState()
        when (boolean) {
            true -> OutlinedTextField(
                modifier = modifier,
                label = { Text(text = KEY_INT) },
                value = int?.toString() ?: "",
                onValueChange = {
                    viewModel.int.value = it.toIntOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )
            else -> TextField(
                modifier = modifier,
                label = { Text(text = KEY_INT) },
                value = int?.toString() ?: "",
                onValueChange = {
                    viewModel.int.value = it.toIntOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )
        }
    }

    @Composable
    private fun LongTextField() {
        val long by viewModel.long.observeAsState()
        val boolean by viewModel.boolean.observeAsState()
        when (boolean) {
            true -> OutlinedTextField(
                modifier = modifier,
                label = { Text(text = KEY_LONG) },
                value = long?.toString() ?: "",
                onValueChange = {
                    viewModel.long.value = it.toLongOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )
            else -> TextField(
                modifier = modifier,
                label = { Text(text = KEY_LONG) },
                value = long?.toString() ?: "",
                onValueChange = {
                    viewModel.long.value = it.toLongOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )
        }
    }

    @Composable
    private fun FloatTextField() {
        val float by viewModel.float.observeAsState()
        val boolean by viewModel.boolean.observeAsState()
        when (boolean) {
            true -> OutlinedTextField(
                modifier = modifier,
                label = { Text(text = KEY_FLOAT) },
                value = float?.toString() ?: "",
                onValueChange = {
                    viewModel.float.value = it.toFloatOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )
            else -> TextField(
                modifier = modifier,
                label = { Text(text = KEY_FLOAT) },
                value = float?.toString() ?: "",
                onValueChange = {
                    viewModel.float.value = it.toFloatOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )
        }
    }

    @Composable
    private fun DoubleTextField() {
        val double by viewModel.double.observeAsState()
        val boolean by viewModel.boolean.observeAsState()
        when (boolean) {
            true -> OutlinedTextField(
                modifier = modifier,
                label = { Text(text = KEY_DOUBLE) },
                value = double?.toString() ?: "",
                onValueChange = {
                    viewModel.double.value = it.toDoubleOrNull()
                },
            )
            else -> TextField(
                modifier = modifier,
                label = { Text(text = KEY_DOUBLE) },
                value = double?.toString() ?: "",
                onValueChange = {
                    viewModel.double.value = it.toDoubleOrNull()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
            )
        }
    }

    @Composable
    private fun StringTextField() {
        val string by viewModel.string.observeAsState()
        val boolean by viewModel.boolean.observeAsState()
        when (boolean) {
            true -> OutlinedTextField(
                modifier = modifier,
                label = { Text(text = KEY_STRING) },
                value = string ?: "",
                onValueChange = {
                    viewModel.string.value = it
                },
            )
            else -> TextField(
                modifier = modifier,
                label = { Text(text = KEY_STRING) },
                value = string ?: "",
                onValueChange = {
                    viewModel.string.value = it
                },
            )
        }
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