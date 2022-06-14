package androidx.essentials.extensions

fun Try(onTry: () -> Unit): Throwable? {
    try {
        onTry.invoke()
    } catch (e: Throwable) {
        return e
    }
    return null
}