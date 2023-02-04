package com.azamovhudstc.noteappplaystore.viewmodel.edit

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity

interface EditScreenViewModel {
    var editNoteLiveData:MutableLiveData<Unit>
    var deleteNoteLiveData:MutableLiveData<Unit>
    fun editNote(noteEntity: NoteEntity)
    fun deleteNote(noteEntity: NoteEntity)
}