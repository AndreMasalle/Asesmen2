package org.d3if3155.asesmen2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3155.asesmen2.data.Contact
import org.d3if3155.asesmen2.database.ContactDao

class MainViewModel(dao: ContactDao) : ViewModel() {


    val data: StateFlow<List<Contact>> = dao.getKontak().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}