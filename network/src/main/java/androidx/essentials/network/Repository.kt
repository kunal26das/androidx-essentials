package androidx.essentials.network

import android.content.Context
import androidx.essentials.network.builder.RoomDatabaseBuilder
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.RoomDatabase
import retrofit2.Retrofit

open class Repository @JvmOverloads constructor(
    val context: Context? = null,
    val retrofit: Retrofit? = null,
) {

    inline fun <reified T> Repository.retrofit(): Lazy<T?> = lazy {
        retrofit?.create(T::class.java)
    }

    inline fun <reified T : RoomDatabase> Repository.roomDatabase(
        name: String
    ) = RoomDatabaseBuilder(context!!, T::class, name)

    inline fun <Key : Any, Value : Any> Repository.pagingSource(
        crossinline getRefreshKey: (state: PagingState<Key, Value>) -> Key?,
        load: Load<Key, Value>,
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