package androidx.essentials.network.local

import android.content.Context
import androidx.essentials.network.Builder
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.reflect.KClass

open class RoomDatabaseBuilder<T : RoomDatabase>(
    private val context: Context,
    private val klass: KClass<T>,
    private val name: String = context.packageName,
    private val builder: (RoomDatabase.Builder<T>.() -> Unit)? = null
) : Builder<T>() {
    override fun initialize(): T {
        return Room.databaseBuilder(context, klass.java, name).apply {
            builder?.invoke(this)
        }.build()
    }
}