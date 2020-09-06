package androidx.essentials.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.essentials.core.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView

class AppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    val toolbar: MaterialToolbar
    private val appBarLayout: AppBarLayout
    private val contentLoadingProgressBar: ContentLoadingProgressBar
    var isLoading: Boolean =
        DEFAULT_LOADING
        set(value) {
            field = value
            when (value) {
                true -> contentLoadingProgressBar.show()
                false -> contentLoadingProgressBar.hide()
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_appbar, this, true).apply {
            contentLoadingProgressBar = findViewById(R.id.contentLoadingProgressBar)
            appBarLayout = findViewById(R.id.materialAppBarLayout)
            appBarLayout.backgroundTintList = cardBackgroundColor
            toolbar = findViewById(R.id.toolbar)
            contentLoadingProgressBar.hide()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        when (layoutParams) {
            is CoordinatorLayout.LayoutParams -> {
                with(layoutParams as CoordinatorLayout.LayoutParams) {
                    behavior = AppBarLayoutBehaviour()
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_LOADING = false
    }

}