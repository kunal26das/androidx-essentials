package androidx.essentials.extensions

object Try {
    inline fun <T> T.Try(block: T.() -> Unit): T {
        try {
            block()
        } catch (e: Exception) {
        }
        return this
    }
}