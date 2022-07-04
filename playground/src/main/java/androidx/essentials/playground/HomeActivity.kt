package androidx.essentials.playground

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.network.NetworkCallback
import androidx.essentials.network.NetworkCallback.Companion.isNetworkAvailable
import androidx.essentials.view.ComposeActivity
import com.google.accompanist.flowlayout.FlowRow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComposeActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val connectivityManager by lazy {
        applicationContext.getSystemService(ConnectivityManager::class.java)
    }

    private val contracts = Feature.values().map {
        registerForActivityResult(it.activityResultContract)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        connectivityManager.registerDefaultNetworkCallback(NetworkCallback)
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
                FlowRow(
                    modifier = Modifier.padding(12.dp),
                ) {
                    Feature.values().forEach { feature ->
                        AssistChip(
                            modifier = Modifier.padding(horizontal = 8.dp),
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

    override fun onStart() {
        super.onStart()
        isNetworkAvailable.observe {
            if (it != true) Toast.makeText(
                this, getString(R.string.no_internet),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        connectivityManager.unregisterNetworkCallback(NetworkCallback)
        super.onDestroy()
    }

}