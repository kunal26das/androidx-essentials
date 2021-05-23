package androidx.essentials.repository

import android.content.Context
import androidx.essentials.preferences.SharedPreferences
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import androidx.room.Room
import androidx.room.RoomDatabase
import retrofit2.Retrofit

open class Repository @JvmOverloads constructor(
    val context: Context? = null,
    val retrofit: Retrofit? = null,
) : SharedPreferences {

    inline fun <reified T> Repository.retrofit(): Lazy<T?> = lazy {
        retrofit?.create(T::class.java)
    }

    inline fun <reified T : RoomDatabase> Repository.roomDatabase(name: String): Lazy<T?> = lazy {
        context?.let { Room.databaseBuilder(it, T::class.java, name).build() }
    }

    inline fun <reified Key : Any, Value : Any> Repository.pageKeyedDataSource(
        crossinline loadInitial: (
            PageKeyedDataSource.LoadInitialParams<Key>,
            PageKeyedDataSource.LoadInitialCallback<Key, Value>
        ) -> Unit,
        crossinline loadBefore: (
            PageKeyedDataSource.LoadParams<Key>,
            PageKeyedDataSource.LoadCallback<Key, Value>
        ) -> Unit,
        crossinline loadAfter: (
            PageKeyedDataSource.LoadParams<Key>,
            PageKeyedDataSource.LoadCallback<Key, Value>
        ) -> Unit,
    ): Lazy<PageKeyedDataSource<Key, Value>> = lazy {
        object : PageKeyedDataSource<Key, Value>() {
            override fun loadInitial(
                params: LoadInitialParams<Key>,
                callback: LoadInitialCallback<Key, Value>
            ) = loadInitial(params, callback)

            override fun loadBefore(
                params: LoadParams<Key>,
                callback: LoadCallback<Key, Value>
            ) = loadBefore(params, callback)

            override fun loadAfter(
                params: LoadParams<Key>,
                callback: LoadCallback<Key, Value>
            ) = loadAfter(params, callback)
        }
    }

    inline fun <reified Key : Any, Value : Any> Repository.itemKeyedDataSource(
        crossinline getKey: (Value) -> Key,
        crossinline loadInitial: (
            ItemKeyedDataSource.LoadInitialParams<Key>,
            ItemKeyedDataSource.LoadInitialCallback<Value>
        ) -> Unit,
        crossinline loadBefore: (
            ItemKeyedDataSource.LoadParams<Key>,
            ItemKeyedDataSource.LoadCallback<Value>
        ) -> Unit,
        crossinline loadAfter: (
            ItemKeyedDataSource.LoadParams<Key>,
            ItemKeyedDataSource.LoadCallback<Value>
        ) -> Unit,
    ): Lazy<ItemKeyedDataSource<Key, Value>> = lazy {
        object : ItemKeyedDataSource<Key, Value>() {
            override fun getKey(item: Value) = getKey(item)

            override fun loadInitial(
                params: LoadInitialParams<Key>,
                callback: LoadInitialCallback<Value>
            ) = loadInitial(params, callback)

            override fun loadBefore(
                params: LoadParams<Key>,
                callback: LoadCallback<Value>
            ) = loadBefore(params, callback)

            override fun loadAfter(
                params: LoadParams<Key>,
                callback: LoadCallback<Value>
            ) = loadAfter(params, callback)
        }
    }

    inline fun <reified Value : Any> Repository.positionalDataSource(
        crossinline loadInitial: (
            PositionalDataSource.LoadInitialParams,
            PositionalDataSource.LoadInitialCallback<Value>
        ) -> Unit,
        crossinline loadRange: (
            PositionalDataSource.LoadRangeParams,
            PositionalDataSource.LoadRangeCallback<Value>
        ) -> Unit,
    ): Lazy<PositionalDataSource<Value>> = lazy {
        object : PositionalDataSource<Value>() {
            override fun loadInitial(
                params: LoadInitialParams,
                callback: LoadInitialCallback<Value>
            ) = loadInitial(params, callback)

            override fun loadRange(
                params: LoadRangeParams,
                callback: LoadRangeCallback<Value>
            ) = loadRange(params, callback)
        }
    }

}