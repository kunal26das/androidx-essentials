package androidx.essentials.playground.hilt

import android.content.Context
import androidx.essentials.playground.network.Retrofit
import androidx.essentials.playground.repository.Api
import androidx.essentials.playground.repository.LibraryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Provider {

    @Provides
    fun getApi() = Retrofit.create(Api::class)

    @Provides
    fun getLibraryRepository(
        @ApplicationContext context: Context,
        api: Api
    ) = LibraryRepository(context, api)

}