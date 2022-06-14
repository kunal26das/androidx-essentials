package androidx.essentials.extensions

class Try {
    companion object {

        fun _try(onTry: () -> Unit): Throwable? {
            try {
                onTry.invoke()
            } catch (e: Throwable) {
                return e
            }
            return null
        }

    }
}