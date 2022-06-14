package androidx.essentials.playground.network

import androidx.essentials.network.builder.GsonBuilder
import androidx.essentials.network.builder.RetrofitBuilder
import androidx.essentials.playground.network.Retrofit.BASE_URL
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit : RetrofitBuilder({
    baseUrl(BASE_URL)
    client(OkHttpClient.getInstance())
    addConverterFactory(GsonConverterFactory.create(GsonBuilder().getInstance()))
}) {
    private const val BASE_URL = "https://play-ground-4b041.firebaseio.com"
}