package org.d3if3155.asesmen2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3155.asesmen2.data.Contact
import org.d3if3155.asesmen2.database.ContactDao
import java.util.Date

class ContactViewModel(private val dao: ContactDao): ViewModel() {

//    fun getContact(id: Long): Contact {
//        return Contact(
//            id,
//            "Andre",
//            "3155",
//            "sesuatu@sesuatu"
//        )
//    }

    fun insert(nama : String, nomor : String, email: String){
        val contact = Contact(
            nama = nama,
            nomor = nomor,
            email = email
        )

        viewModelScope.launch(Dispatchers.IO){
            dao.insert(contact)
        }
    }

    suspend fun getContact(id: Long): Contact?{
        return dao.getKontakById(id)
    }

    fun update(id: Long,nama : String, nomor : String, email: String){
        val contact = Contact(
            id = id,
            nama = nama,
            nomor = nomor,
            email = email
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(contact)
        }
    }

    fun delete(id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}