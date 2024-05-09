package org.d3if3155.asesmen2.screen

import androidx.lifecycle.ViewModel
import org.d3if3155.asesmen2.data.Contact

class UbahViewModel : ViewModel() {

    fun getContact(id: Long): Contact{
        return Contact(
            id,
            "Andre",
            "3155",
            "sesuatu@sesuatu"
        )
    }
}