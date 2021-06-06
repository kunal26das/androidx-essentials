package androidx.essentials.playground.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RepositoryViewModel : ViewModel(), KoinComponent {

    private val repository by inject<LibraryRepository>()
    val pager
        get() = Pager(PagingConfig(PAGE_SIZE)) {
            repository.libraries
        }.flow.asLiveData(
            viewModelScope.coroutineContext
        )

}