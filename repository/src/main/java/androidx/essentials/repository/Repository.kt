package androidx.essentials.repository

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.Room
import androidx.room.RoomDatabase
import retrofit2.Retrofit

open class Repository @JvmOverloads constructor(
    val context: Context? = null,
    val retrofit: Retrofit? = null,
) {

    inline fun <reified T> Repository.retrofit(): Lazy<T?> = lazy {
        retrofit?.create(T::class.java)
    }

    inline fun <reified T : RoomDatabase> Repository.roomDatabase(name: String): Lazy<T?> = lazy {
        context?.let { Room.databaseBuilder(it, T::class.java, name).build() }
    }

    inline fun <reified Key : Any, Value : Any> Repository.pagingSource(
        crossinline getRefreshKey: (
            state: PagingState<Key, Value>
        ) -> Key?,
        crossinline load: (
            params: PagingSource.LoadParams<Key>
        ) -> PagingSource.LoadResult<Key, Value>,
    ): Lazy<PagingSource<Key, Value>> = lazy {
        object : PagingSource<Key, Value>() {
            override fun getRefreshKey(state: PagingState<Key, Value>): Key? {
                return getRefreshKey.invoke(state)
            }

            override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value> {
                return load.invoke(params)
            }
        }
    }

}