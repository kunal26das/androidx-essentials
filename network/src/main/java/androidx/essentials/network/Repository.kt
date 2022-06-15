package androidx.essentials.network

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class Repository @Inject constructor(
    @ApplicationContext val context: Context
) {

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