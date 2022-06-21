package androidx.essentials.playground.preferences

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.essentials.view.ComposeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesActivity : ComposeActivity() {

    private val viewModel by viewModels<PreferencesViewModel>()

    @Composable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            StringTextField()
        }
    }

    @Composable
    private fun StringTextField() {
        var string by remember { mutableStateOf(viewModel.string.value) }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text(text = PreferencesViewModel.KEY_STRING) },
            value = when (string.isNullOrEmpty()) {
                false -> "$string"
                true -> ""
            },
            onValueChange = {
                viewModel.string.value = it
                string = it
            },
        )
    }

}