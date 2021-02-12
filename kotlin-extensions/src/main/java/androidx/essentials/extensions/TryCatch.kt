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