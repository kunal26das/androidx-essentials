package androidx.essentials.playground.firebase

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.playground.R
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseActivity : ComposeActivity() {

    @Inject
    lateinit var firebase: Firebase

    @Composable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            TokenTextField()
            UUIDTextField()
        }
    }

    @Composable
    private fun TokenTextField() {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = getString(R.string.token)) },
            value = firebase.token.value ?: "",
            onValueChange = {},
            readOnly = true,
        )
    }

    @Composable
    private fun UUIDTextField() {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = getString(R.string.uuid)) },
            value = firebase.uuid,
            onValueChange = {},
            readOnly = true,
        )
    }

}