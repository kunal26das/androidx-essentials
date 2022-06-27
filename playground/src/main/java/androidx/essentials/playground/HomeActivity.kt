package androidx.essentials.playground

import android.content.Context
import android.net.ConnectivityManager
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
import androidx.essentials.network.NetworkCallback
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComposeActivity() {

    private var connectivityManager: ConnectivityManager? = null

    private val contracts = Feature.values().map {
        registerForActivityResult(it.activityResultContract)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        connectivityManager?.registerDefaultNetworkCallback(NetworkCallback)
    }

    @Composable
    override fun setContent() {
        super.setContent()
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

    override fun onDetach() {
        connectivityManager?.unregisterNetworkCallback(NetworkCallback)
        super.onDetach()
    }

}