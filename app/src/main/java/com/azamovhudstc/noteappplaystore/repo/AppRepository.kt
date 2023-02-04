package com.azamovhudstc.noteappplaystore.repo

import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun deleteNote(note: NoteEntity):Flow<String>
    fun  addNote(note: NoteEntity):Flow<String>
    fun  editNote(note: NoteEntity):Flow<String>
    fun getAllNotes():Flow<List<NoteEntity>>

}