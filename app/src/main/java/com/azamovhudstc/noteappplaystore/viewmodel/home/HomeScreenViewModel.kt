package com.azamovhudstc.noteappplaystore.viewmodel.home

import androidx.lifecycle.MutableLiveData
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity

interface HomeScreenViewModel {
    val openAddNoteScreen: MutableLiveData<Unit>
    val progressState: MutableLiveData<Boolean>
    val openUrlDialog: MutableLiveData<Unit>
    val getAllNote: MutableLiveData<List<NoteEntity>>
    fun openAddNoteScreen()
    fun getAllNote()
    fun addUrl()

}