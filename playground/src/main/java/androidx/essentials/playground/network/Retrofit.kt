package androidx.essentials.playground.network

import androidx.essentials.network.RetrofitBuilder
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class Retrofit : RetrofitBuilder({
    baseUrl(BASE_URL)
    client(OkHttpClient.INSTANCE)
    addConverterFactory(GsonConverterFactory.create(Gson.INSTANCE))
}) {

    fun <T : Any> create(klass: KClass<T>): T {
        return INSTANCE.create(klass.java)
    }

    companion object {
        private const val BASE_URL = "https://play-ground-4b041.firebaseio.com"
    }
}