package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.essentials.view.ComposeActivity

class HomeActivity : ComposeActivity() {

    private val contracts = Feature.values().map {
        registerForActivityResult(it.activityResultContract)
    }

    @Composable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Column {
            Feature.values().forEach { feature ->
                TextButton(
                    content = { Text(feature.name) },
                    onClick = {
                        contracts[feature.ordinal].launch(null)
                    }
                )
            }
        }
    }

}