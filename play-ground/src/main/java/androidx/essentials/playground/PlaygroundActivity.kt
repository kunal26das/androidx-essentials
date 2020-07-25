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
    private lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playground)
        content = findViewById(android.R.id.content)
        content.onGlobalLayoutListener {
            bottomSheetView.peekHeight = it.height / 2
        }
        networkCallback = NetworkCallback.getInstance(this)
        locationProvider = LocationProvider.getInstance(this)
    }

    override fun onStart() {
        super.onStart()
        subscribeEvents()
        registerNetworkCallback()
        setOnLocationChangeListener()
    }

    private fun subscribeEvents() {
        Events.subscribe(String::class.java) {
            Snackbar.make(content, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun registerNetworkCallback() {
        networkCallback.register({
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

    @SuppressLint("SetTextI18n")
    private fun setOnLocationChangeListener() {
        locationProvider.setOnLocationChangeListener { latitude, longitude ->
            materialTextView.text = "$latitude\n$longitude"
        }
    }

    override fun onStop() {
        networkCallback.unregister(this)
        super.onStop()
    }
}
