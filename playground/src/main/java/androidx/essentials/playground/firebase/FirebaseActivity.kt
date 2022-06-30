package androidx.essentials.playground.firebase

import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.essentials.network.NetworkCallback.Companion.isNetworkAvailable
import androidx.essentials.playground.Feature
import androidx.essentials.playground.R
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseActivity : ComposeActivity() {

    private val viewModel by viewModels<FirebaseViewModel>()

    @Composable
    override fun setContent() {
        super.setContent()
        viewModel.getToken()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0))
        ) {
            LargeTopAppBar(title = { Text(text = Feature.Firebase.name) })
            TokenTextField()
        }
    }

    @Preview
    @Composable
    private fun TokenTextField() {
        val token by viewModel.token.observeAsState()
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp,
                ),
            label = { Text(text = getString(R.string.token)) },
            value = "$token",
            onValueChange = {},
            readOnly = true,
        )
    }

    override fun onStart() {
        super.onStart()
        isNetworkAvailable.observe {
            if (!it) Toast.makeText(
                this, getString(R.string.no_internet),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}