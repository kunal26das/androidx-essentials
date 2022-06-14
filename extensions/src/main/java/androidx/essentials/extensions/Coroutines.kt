package androidx.essentials.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {

    inline fun <T> T.default(crossinline block: T.() -> Unit): T {
        CoroutineScope(Dispatchers.Default).launch {
            block()
        }
        return this
    }

    inline fun <T> T.main(crossinline block: T.() -> Unit): T {
        CoroutineScope(Dispatchers.Main).launch {
            block()
        }
        return this
    }

}