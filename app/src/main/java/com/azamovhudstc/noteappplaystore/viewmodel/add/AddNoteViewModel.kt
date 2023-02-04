package com.azamovhudstc.noteappplaystore.viewmodel.add

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity

interface AddNoteViewModel {
    val addNoteViewModel:MutableLiveData<Unit>
    fun addNote(noteEntity: NoteEntity)
}