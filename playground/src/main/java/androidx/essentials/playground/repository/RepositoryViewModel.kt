package androidx.essentials.playground.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    val pager
        get() = Pager(PagingConfig(PAGE_SIZE)) {
            libraryRepository.libraries
        }.flow.asLiveData(
            viewModelScope.coroutineContext
        )

}