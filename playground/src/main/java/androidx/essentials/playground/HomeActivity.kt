package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.view.ComposeActivity

class HomeActivity : ComposeActivity() {

    private val contracts = Feature.values().map {
        registerForActivityResult(it.activityResultContract)
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
        ) {
            LargeTopAppBar(
                title = { Text(text = getString(R.string.playground)) }
            )
            Feature.values().forEach { feature ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                ) {
                    AssistChip(
                        modifier = Modifier.padding(4.dp),
                        label = { Text(feature.name) },
                        onClick = {
                            contracts[feature.ordinal].launch(null)
                        }
                    )
                    if (feature.compose) AssistChip(
                        modifier = Modifier.padding(4.dp),
                        label = { Text(getString(R.string.compose)) },
                        enabled = false,
                        onClick = {},
                    )
                }
            }
        }
    }

}