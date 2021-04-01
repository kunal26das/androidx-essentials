package androidx.essentials.extensions

object TryCatch {

    inline fun <T> T.Try(
        Try: T.() -> Unit
    ): T {
        try {
            Try()
        } catch (e: Exception) {
        }
        return this
    }

    inline fun <reified T : Exception> Catch(
        Catch: (T?) -> Unit,
        Try: () -> Unit,
    ) = try {
        Try()
    } catch (throwable: Throwable) {
        when (throwable) {
            is T -> Catch.invoke(throwable)
            else -> Catch.invoke(null)
        }
    }

}