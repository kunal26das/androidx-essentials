package androidx.essentials.playground

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.essentials.network.NetworkCallback
import com.google.android.material.snackbar.Snackbar

class PlaygroundActivity : AppCompatActivity() {

    private lateinit var content: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playground)
        content = findViewById(android.R.id.content)
        networkCallback()
    }

    private fun networkCallback() {
        val networkCallback = NetworkCallback(this).register({
            Snackbar.make(content, "Online", Snackbar.LENGTH_INDEFINITE).show()
        }, {
            Snackbar.make(content, "Offline", Snackbar.LENGTH_INDEFINITE).show()
        })
    }
}
