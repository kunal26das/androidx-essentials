package androidx.essentials.extensions

object Try {

    inline fun <T> T.Try(
        block: T.() -> Unit
    ): T {
        try {
            block()
        } catch (e: Exception) {
        }
        return this
    }

    inline fun <reified T : Exception> T.Try(
        Try: () -> Unit,
        Catch: (T?) -> Unit
    ) = try {
        Try()
    } catch (exception: Exception) {
        when (exception) {
            is T -> Catch.invoke(exception)
            else -> Catch.invoke(null)
        }
    }
}