package androidx.essentials.playground.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextSwitch(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean?,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface {
            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
            )
        }
        Switch(
            modifier = Modifier.padding(8.dp),
            checked = checked ?: false,
            onCheckedChange = onCheckedChange,
        )
    }
}