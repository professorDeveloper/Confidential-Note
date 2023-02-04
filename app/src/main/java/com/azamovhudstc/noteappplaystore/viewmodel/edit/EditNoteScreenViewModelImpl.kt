package com.azamovhudstc.noteappplaystore.viewmodel.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
import com.azamovhudstc.noteappplaystore.repo.imp.AppRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditNoteScreenViewModelImpl @Inject constructor(var repository: AppRepositoryImp):EditScreenViewModel,ViewModel() {
    override var editNoteLiveData: MutableLiveData<Unit> = MutableLiveData()
    override var deleteNoteLiveData: MutableLiveData<Unit> = MutableLiveData()

    override fun editNote(noteEntity: NoteEntity) {
        repository.editNote(noteEntity).onEach {
            if (it=="Edited"){
                editNoteLiveData.value=Unit
            }
        }.launchIn(viewModelScope)
    }

    override fun deleteNote(noteEntity: NoteEntity) {
        repository.deleteNote(noteEntity).onEach {
            if (it=="Deleted"){
                deleteNoteLiveData.value=Unit
            }
        }.launchIn(viewModelScope)
    }
}