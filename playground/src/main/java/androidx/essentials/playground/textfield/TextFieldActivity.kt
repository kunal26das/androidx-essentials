package androidx.essentials.playground.textfield

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.Feature
import androidx.essentials.playground.R
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TextFieldActivity : ComposeActivity() {

    private val viewModel by viewModels<TextFieldViewModel>()

    private val modifier: Modifier
        @Composable get() {
            val style by viewModel.style.observeAsState()
            return Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = when (style) {
                        true -> 5.dp
                        else -> 8.dp
                    },
                )
        }

    @Composable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Column(modifier.verticalScroll(ScrollState(0))) {
            LargeTopAppBar(
                title = { Text(text = Feature.TextField.name) }
            )
            TextField()
            LabelTextField()
            PlaceholderTextField()
            StyleSwitch()
            ReadOnlySwitch()
            MandatorySwitch()
        }
    }

    @Composable
    private fun StyleSwitch() {
        val style by viewModel.style.observeAsState()
        TextSwitch(getString(R.string.style), style) {
            viewModel.style.value = it
        }
    }

    @Composable
    private fun ReadOnlySwitch() {
        val readOnly by viewModel.readOnly.observeAsState()
        TextSwitch(getString(R.string.read_only), readOnly) {
            viewModel.readOnly.value = it
        }
    }

    @Composable
    private fun MandatorySwitch() {
        val mandatory by viewModel.mandatory.observeAsState()
        TextSwitch(getString(R.string.mandatory), mandatory) {
            viewModel.mandatory.value = it
        }
    }

    @Composable
    private fun TextSwitch(
        text: String, flag: Boolean?,
        onCheckedChange: (Boolean) -> Unit
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = text,
                )
            }
            Switch(
                modifier = Modifier.padding(8.dp),
                checked = flag ?: false,
                onCheckedChange = onCheckedChange,
            )
        }
    }

    @Composable
    private fun TextField() {
        val text by viewModel.text.observeAsState()
        val label by viewModel.label.observeAsState()
        val style by viewModel.style.observeAsState()
        val readOnly by viewModel.readOnly.observeAsState()
        val mandatory by viewModel.mandatory.observeAsState()
        val placeholder by viewModel.placeholder.observeAsState()
        when (style) {
            true -> OutlinedTextField(
                modifier = modifier,
                value = text ?: "",
                readOnly = readOnly ?: false,
                label = { Text(text = label ?: "") },
                onValueChange = { viewModel.text.value = it },
                placeholder = { Text(text = placeholder ?: "") },
                isError = mandatory == true && text.isNullOrEmpty(),
            )
            else -> TextField(
                modifier = modifier,
                value = text ?: "",
                readOnly = readOnly ?: false,
                label = { Text(text = label ?: "") },
                onValueChange = { viewModel.text.value = it },
                placeholder = { Text(text = placeholder ?: "") },
                isError = mandatory == true && text.isNullOrEmpty(),
            )
        }
    }

    @Composable
    private fun LabelTextField() {
        val label by viewModel.label.observeAsState()
        val style by viewModel.style.observeAsState()
        when (style) {
            true -> OutlinedTextField(
                modifier = modifier,
                value = label ?: "",
                onValueChange = { viewModel.label.value = it },
                label = { Text(text = getString(R.string.label)) },
            )
            else -> TextField(
                modifier = modifier,
                value = label ?: "",
                onValueChange = { viewModel.label.value = it },
                label = { Text(text = getString(R.string.label)) },
            )
        }
    }

    @Composable
    private fun PlaceholderTextField() {
        val style by viewModel.style.observeAsState()
        val placeholder by viewModel.placeholder.observeAsState()
        when (style) {
            true -> OutlinedTextField(
                modifier = modifier,
                value = placeholder ?: "",
                onValueChange = { viewModel.placeholder.value = it },
                label = { Text(text = getString(R.string.placeholder)) },
            )
            else -> TextField(
                modifier = modifier,
                value = placeholder ?: "",
                onValueChange = { viewModel.placeholder.value = it },
                label = { Text(text = getString(R.string.placeholder)) },
            )
        }
    }

}