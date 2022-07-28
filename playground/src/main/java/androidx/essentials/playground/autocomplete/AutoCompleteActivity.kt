package androidx.essentials.playground.autocomplete

import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.Feature
import androidx.essentials.playground.R
import androidx.essentials.playground.compose.TextSwitch
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class AutoCompleteActivity : ComposeActivity() {

    private val viewModel by viewModels<AutoCompleteViewModel>()

    @Composable
    override fun Content() {
        super.Content()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
        ) {
            LargeTopAppBar(
                title = { Text(text = Feature.AutoComplete.name) }
            )
            AutoComplete()
            FilterSwitch()
            ReadOnlySwitch()
        }
    }

    @Composable
    private fun AutoComplete() {
        val features = Feature.values().map { it.name }
        val filter by viewModel.filter.observeAsState()
        val expanded by viewModel.expanded.observeAsState()
        val readOnly by viewModel.readOnly.observeAsState()
        val selection by viewModel.selection.observeAsState()
        ExposedDropdownMenuBox(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            expanded = expanded ?: false,
            onExpandedChange = {
                viewModel.expanded.value = !(expanded ?: false)
            },
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    viewModel.selection.value = it
                    viewModel.expanded.value = true
                },
                value = selection ?: "",
                readOnly = readOnly ?: false,
                label = { Text(text = getString(R.string.feature)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded ?: false) },
            )
            val filteredList = when {
                selection.isNullOrEmpty() -> features
                filter == true -> features.filter {
                    it.startsWith("$selection", true)
                }
                else -> features
            }
            if (filteredList.isNotEmpty()) {
                ExposedDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded ?: false,
                    onDismissRequest = {
                        viewModel.expanded.value = false
                    }
                ) {
                    filteredList.forEach { feature ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(text = feature) },
                            onClick = {
                                viewModel.selection.value = feature
                                viewModel.expanded.value = false
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun FilterSwitch() {
        val filter by viewModel.filter.observeAsState()
        TextSwitch(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = getString(R.string.filter),
            checked = filter ?: false,
            onCheckedChange = {
                viewModel.filter.value = it
            },
        )
    }

    @Composable
    private fun ReadOnlySwitch() {
        val readOnly by viewModel.readOnly.observeAsState()
        TextSwitch(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = getString(R.string.read_only),
            checked = readOnly ?: false,
            onCheckedChange = {
                viewModel.readOnly.value = it
            },
        )
    }

}