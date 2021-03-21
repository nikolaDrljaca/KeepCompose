package com.example.keepcompose.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.keepcompose.model.KeepNote
import kotlinx.coroutines.flow.Flow

@Dao
interface KeepNoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keepNote: KeepNote)

    @Query("SELECT * FROM keep_note_table ORDER BY id")
    suspend fun getNoteList(): List<KeepNote>

    @Query("SELECT * FROM keep_note_table WHERE id=:id")
    suspend fun getKeepNote(id: Int): KeepNote

    @Query("SELECT * FROM keep_note_table WHERE title LIKE :query")
    fun queryKeepNotes(query: String): LiveData<List<KeepNote>>

    @Delete
    suspend fun delete(keepNote: KeepNote)
}