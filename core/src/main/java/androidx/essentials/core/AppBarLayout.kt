package androidx.essentials.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.widget.ContentLoadingProgressBar
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView

class AppBarLayout @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null
) : MaterialCardView(context, attributes) {

    var toolbar: MaterialToolbar
    var loading = false
        set(value) {
            field = value
            when (value) {
                true -> contentLoadingProgressBar.show()
                false -> contentLoadingProgressBar.hide()
            }
        }
    private var contentLoadingProgressBar: ContentLoadingProgressBar

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_appbar, this, true).apply {
            contentLoadingProgressBar = findViewById(R.id.contentLoadingProgressBar)
            toolbar = findViewById(R.id.materialToolbar)
            loading = false
        }
    }
}