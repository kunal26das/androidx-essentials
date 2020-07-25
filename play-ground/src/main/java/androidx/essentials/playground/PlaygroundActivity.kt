package androidx.essentials.playground

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.essentials.events.Events
import androidx.essentials.extensions.Coroutines.main
import androidx.essentials.extensions.View.onGlobalLayoutListener
import androidx.essentials.location.LocationProvider
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
        initLocationProvider()
    }

    override fun onStart() {
        super.onStart()
        subscribeEvents()
        initNetworkCallback()
    }

    @SuppressLint("SetTextI18n")
    private fun initLocationProvider() {
        LocationProvider.getInstance(this).setOnLocationChangeListener { latitude, longitude ->
            materialTextView.text = "$latitude\n$longitude"
        }
    }

    private fun subscribeEvents() {
        Events.subscribe(String::class.java) {
            Snackbar.make(content, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun initNetworkCallback() {
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
        networkCallback.unregister()
        super.onStop()
    }
}
