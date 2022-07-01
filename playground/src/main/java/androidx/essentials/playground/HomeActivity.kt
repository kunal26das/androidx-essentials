package androidx.essentials.playground

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.network.NetworkCallback
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComposeActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
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
        Box {
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
                    }
                }
            }
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                text = { Text(text = getString(R.string.clear)) },
                onClick = { sharedPreferences.edit().clear().apply() },
                icon = {},
            )
        }
    }

    override fun onDetach() {
        connectivityManager?.unregisterNetworkCallback(NetworkCallback)
        super.onDetach()
    }

}