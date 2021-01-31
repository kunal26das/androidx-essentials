package androidx.essentials.io.input

import android.content.Context
import android.os.Build
import android.view.inputmethod.InputMethodManager

object InputMethodManager {

    private var inputMethodManager: InputMethodManager? = null

    fun getInstance(context: Context): InputMethodManager {
        if (inputMethodManager != null) {
            return inputMethodManager!!
        }
        synchronized(this) {
            inputMethodManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.getSystemService(InputMethodManager::class.java)
            } else {
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            return inputMethodManager!!
        }
    }

}