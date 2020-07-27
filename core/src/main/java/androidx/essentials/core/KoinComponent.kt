package androidx.essentials.core

import org.koin.core.KoinComponent

object KoinComponent {
    val koinComponent = object : KoinComponent {}
    inline fun <reified T> inject() = koinComponent.getKoin().inject<T>()
}