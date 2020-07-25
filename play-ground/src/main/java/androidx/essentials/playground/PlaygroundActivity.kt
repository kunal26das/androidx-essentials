package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.essentials.events.Events
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.extensions.View.onGlobalLayoutListener
import androidx.essentials.network.NetworkCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_playground.*

class PlaygroundActivity : AppCompatActivity() {

    private lateinit var content: View
    private lateinit var networkCallback: NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playground)
        content = findViewById(android.R.id.content)
        content.onGlobalLayoutListener {
            bottomSheetView.peekHeight = it.height / 2
        }
    }

    override fun onStart() {
        super.onStart()
        subscribeEvents()
        networkCallback()
    }

    private fun subscribeEvents() {
        Events.subscribe(String::class.java) {
            Snackbar.make(content, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun networkCallback() {
        networkCallback = NetworkCallback(this).register({
            main {
                bottomSheetView.expand()
                bottomSheetView.isDraggable = false
            }
            Events.publish(getString(R.string.online))
        }, {
            main {
                bottomSheetView.collapse()
                bottomSheetView.isDraggable = true
            }
            Events.publish(getString(R.string.offline))
        })
    }

    override fun onStop() {
        super.onStop()
        networkCallback.unregister()
    }
}
