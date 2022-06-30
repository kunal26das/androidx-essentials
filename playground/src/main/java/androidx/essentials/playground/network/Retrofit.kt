package androidx.essentials.playground.network

import androidx.essentials.network.RetrofitBuilder
import androidx.essentials.playground.network.Retrofit.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

object Retrofit : RetrofitBuilder({
    baseUrl(BASE_URL)
    client(OkHttpClient.INSTANCE)
    addConverterFactory(GsonConverterFactory.create(Gson.INSTANCE))
}) {

    private const val BASE_URL = "https://play-ground-4b041.firebaseio.com"

    fun <T : Any> create(klass: KClass<T>): T {
        return INSTANCE.create(klass.java)
    }

}