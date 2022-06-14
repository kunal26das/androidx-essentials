package androidx.essentials.io.input

import android.content.Context
import android.view.inputmethod.InputMethodManager

object InputMethodManager {

    private var inputMethodManager: InputMethodManager? = null

    fun getInstance(context: Context): InputMethodManager {
        if (inputMethodManager != null) {
            return inputMethodManager!!
        }
        synchronized(this) {
            inputMethodManager = context.getSystemService(InputMethodManager::class.java)
            return inputMethodManager!!
        }
    }

}