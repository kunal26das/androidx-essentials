package androidx.essentials.extensions

object Try {
    inline fun <T> T.Try(block: T.() -> Unit) = try {
        block()
    } catch (e: Exception) {
    }
}