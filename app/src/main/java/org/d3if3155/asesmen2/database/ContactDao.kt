package org.d3if3155.asesmen2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.d3if3155.asesmen2.data.Contact
import java.util.concurrent.Flow

@Dao
interface ContactDao {

    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT*FROM contact ORDER BY nama")
    fun getKontak(): kotlinx.coroutines.flow.Flow<List<Contact>>

    @Query("SELECT * FROM contact WHERE id =:id")
    suspend fun getKontakById(id: Long): Contact?

    @Query("DELETE FROM contact WHERE id =:id")
    suspend fun deleteById(id: Long)


}