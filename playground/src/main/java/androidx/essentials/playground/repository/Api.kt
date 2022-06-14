package androidx.essentials.playground.repository

import android.view.MenuItem
import retrofit2.http.GET
import retrofit2.http.Query

sealed interface Api {

    @GET("/page.json")
    suspend fun getPage(
        @Query("page") page: Int,
        @Query("page_size") limit: Int = page_size,
    ): List<MenuItem>

    companion object {
        const val page_size = 10
    }

}