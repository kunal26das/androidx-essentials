package androidx.essentials.playground.firebase

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.R
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseActivity : ComposeActivity() {

    private val viewModel by viewModels<FirebaseViewModel>()

    @Composable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getToken()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            TokenTextField()
        }
    }

    @Preview
    @Composable
    private fun TokenTextField() {
        val token = viewModel.token.observeAsState()
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = getString(R.string.token)) },
            value = "${token.value}",
            onValueChange = {},
            readOnly = true,
        )
    }

}