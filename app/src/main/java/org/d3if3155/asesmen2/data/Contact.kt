package org.d3if3155.asesmen2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long= 0L,
    val nama: String,
    val nomor: String,
    val email: String
)
