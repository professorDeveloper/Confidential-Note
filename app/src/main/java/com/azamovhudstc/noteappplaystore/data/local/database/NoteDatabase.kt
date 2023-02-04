package com.azamovhudstc.noteappplaystore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azamovhudstc.noteappplaystore.data.local.dao.NoteDao
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun bookDao(): NoteDao

}