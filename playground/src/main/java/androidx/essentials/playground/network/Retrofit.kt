package androidx.essentials.playground.network

import androidx.essentials.network.builder.RetrofitBuilder
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class Retrofit : RetrofitBuilder({
    baseUrl(BASE_URL)
    client(OkHttpClient().getInstance())
    addConverterFactory(GsonConverterFactory.create(Gson().getInstance()))
}) {

    fun <T : Any> create(klass: KClass<T>): T {
        return getInstance().create(klass.java)
    }

    companion object {
        private const val BASE_URL = "https://play-ground-4b041.firebaseio.com"
    }
}