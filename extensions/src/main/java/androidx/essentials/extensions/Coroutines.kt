package androidx.essentials.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {
    inline fun <T> T.default(crossinline block: T.() -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            block()
        }
    }

    inline fun <T> T.io(crossinline block: T.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            block()
        }
    }

    inline fun <T> T.main(crossinline block: T.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            block()
        }
    }

    inline fun <T> T.unconfined(crossinline block: T.() -> Unit) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            block()
        }
    }
}