package androidx.essentials.playground.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.Feature
import androidx.essentials.playground.R
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationActivity : ComposeActivity() {

    private val viewModel by viewModels<LocationViewModel>()

    private val locationRepositoryPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it.values.isNotEmpty()) {
            if (it.values.reduce { a, b -> a or b }) {
                viewModel.getLastLocation()
            }
        }
    }

    @Composable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationRepositoryPermission.launch(
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
        ) {
            LargeTopAppBar(
                title = { Text(text = Feature.Location.name) }
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
                    LatitudeTextField()
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    LongitudeTextField()
                }
            }
        }
    }

    @Composable
    private fun LatitudeTextField() {
        val latitude by viewModel.latitude.observeAsState()
        TextField(
            label = { Text(text = getString(R.string.latitude)) },
            value = latitude?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun LongitudeTextField() {
        val longitude by viewModel.longitude.observeAsState()
        TextField(
            label = { Text(text = getString(R.string.longitude)) },
            value = longitude?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
        )
    }

}
