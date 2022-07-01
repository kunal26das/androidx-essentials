package androidx.essentials.playground.chips

import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.Feature
import androidx.essentials.view.ComposeActivity
import com.google.accompanist.flowlayout.FlowRow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class ChipsActivity : ComposeActivity() {

    private val viewModel by viewModels<ChipsViewModel>()

    @Composable
    override fun setContent() {
        super.setContent()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
        ) {
            LargeTopAppBar(
                title = { Text(text = Feature.Chips.name) }
            )
            Chips()
            Selection()
        }
    }

    @Composable
    private fun Chips() {
        val selection by viewModel.selection.observeAsState()
        FlowRow(
            modifier = Modifier.padding(8.dp),
        ) {
            Feature.values().forEach { feature ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    selected = selection?.contains(feature.name) == true,
                    label = { Text(text = feature.name) },
                    onClick = {
                        viewModel.selection.value?.let {
                            if (it.contains(feature.name)) {
                                it.remove(feature.name)
                            } else it.add(feature.name)
                            viewModel.selection.value = it
                        }
                    },
                )
            }
        }
    }

    @Composable
    private fun Selection() {
        val selection by viewModel.selection.observeAsState()
        FlowRow(
            modifier = Modifier.padding(8.dp),
        ) {
            selection?.forEach { feature ->
                FilterChip(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    label = { Text(text = feature) },
                    selected = false,
                    onClick = {
                        viewModel.selection.value?.let {
                            it.remove(feature)
                            viewModel.selection.value = it
                        }
                    },
                )
            }
        }
    }

}