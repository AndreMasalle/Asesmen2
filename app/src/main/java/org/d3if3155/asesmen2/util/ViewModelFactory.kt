package org.d3if3155.asesmen2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3155.asesmen2.database.ContactDao
import org.d3if3155.asesmen2.screen.ContactViewModel
import org.d3if3155.asesmen2.screen.MainViewModel
//import org.d3if3155.asesmen2.screen.UbahViewModel

class ViewModelFactory(private val dao: ContactDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dao) as T
        }
        else if (modelClass.isAssignableFrom(ContactViewModel::class.java)){
            return ContactViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}