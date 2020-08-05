package androidx.essentials.playground.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.essentials.playground.R
import com.google.android.material.textview.MaterialTextView

class ListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    val title: MaterialTextView
    val subtitle: MaterialTextView

    init {
        LayoutInflater.from(context).inflate(R.layout.item_list, this, true).apply {
            title = findViewById(R.id.title)
            subtitle = findViewById(R.id.subtitle)
        }
    }

}