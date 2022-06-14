package androidx.essentials.network

import androidx.paging.PagingSource

fun interface Load<Key : Any, Value : Any> {
    suspend fun invoke(params: PagingSource.LoadParams<Key>): PagingSource.LoadResult<Key, Value>
}