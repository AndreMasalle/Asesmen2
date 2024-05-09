package org.d3if3155.asesmen2.screen

import androidx.lifecycle.ViewModel
import org.d3if3155.asesmen2.data.Contact

class MainViewModel : ViewModel() {

    val data = getDataDummy()
    private fun getDataDummy() : List<Contact> {
        val data = mutableListOf<Contact>()
        for (i in 29 downTo 20){
            data.add(
                Contact(
                    i.toLong(),
                    "Andre",
                    "$i",
                    "sesuatu@sesuatu"

                )
            )
        }
        return data
    }
}