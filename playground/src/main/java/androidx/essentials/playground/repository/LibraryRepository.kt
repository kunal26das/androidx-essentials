package androidx.essentials.playground.repository

import android.content.Context
import android.view.MenuItem
import androidx.essentials.network.Repository
import androidx.paging.PagingSource
import retrofit2.Retrofit

class LibraryRepository(context: Context, retrofit: Retrofit) : Repository(context, retrofit) {

    private val api by retrofit<Api>()
    val libraries by pagingSource<Int, MenuItem>({
        it.anchorPosition?.let { it1 ->
            it.closestItemToPosition(it1)?.itemId
        }
    }, {
        try {
            val list = api?.getPage(it.key ?: 0)?.blockingGet()
            PagingSource.LoadResult.Page(emptyList(), it.key?.minus(1), it.key?.plus(1))
        } catch (e: Throwable) {
            PagingSource.LoadResult.Error(e)
        }
    })

}