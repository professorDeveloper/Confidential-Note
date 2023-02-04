package com.azamovhudstc.noteappplaystore.data.local.dao
import androidx.room.*
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity


@Dao
interface NoteDao {
    @Query("SELECT * FROM NoteEntity ORDER BY id DESC")
    fun getNotes():List<NoteEntity>
    @Update
    fun editNote(noteEntity: NoteEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity?)
    @Delete
    fun deleteNote(noteEntity: NoteEntity?)
}
