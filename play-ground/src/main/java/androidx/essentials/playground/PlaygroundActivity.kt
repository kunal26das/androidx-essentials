package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.extensions.View.onGlobalLayoutListener
import androidx.essentials.network.NetworkCallback
import kotlinx.android.synthetic.main.activity_playground.*

class PlaygroundActivity : AppCompatActivity() {

    private lateinit var content: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playground)
        content = findViewById(android.R.id.content)
        content.onGlobalLayoutListener {
            bottomSheetView.peekHeight = it.height / 2
        }
    }

    override fun onResume() {
        super.onResume()
        networkCallback()
    }

    private fun networkCallback() {
        NetworkCallback(this).register({
            main {
                materialTextView.text = getString(R.string.online)
                bottomSheetView.expand()
            }
        }, {
            main {
                materialTextView.text = getString(R.string.offline)
                bottomSheetView.collapse()
            }
        })
    }
}
