package androidx.essentials.core.injector

import org.koin.core.KoinComponent

object KoinComponent {

    val koinComponent = object : KoinComponent {}
    inline fun <reified T> Any.inject() = koinComponent.getKoin().inject<T>()

}