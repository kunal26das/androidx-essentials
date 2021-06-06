package androidx.essentials.playground.repository

import android.view.MenuItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

sealed interface Api {

    @GET("/page.json")
    fun getPage(
        @Query("page") page: Int,
        @Query("page_size") limit: Int = page_size,
    ): Single<List<MenuItem>>

    companion object {
        const val page_size = 10
    }

}