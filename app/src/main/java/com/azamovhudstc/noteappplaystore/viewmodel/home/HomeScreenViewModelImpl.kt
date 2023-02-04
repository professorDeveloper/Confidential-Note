package com.azamovhudstc.noteappplaystore.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
import com.azamovhudstc.noteappplaystore.repo.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModelImpl @Inject constructor(private val repository: AppRepository) :
    ViewModel(), HomeScreenViewModel {
    override val openAddNoteScreen: MutableLiveData<Unit> = MutableLiveData()
    override val progressState: MutableLiveData<Boolean> = MutableLiveData()
    override val openUrlDialog: MutableLiveData<Unit> = MutableLiveData()
    override val getAllNote: MutableLiveData<List<NoteEntity>> = MutableLiveData()

    override fun openAddNoteScreen() {
        openAddNoteScreen.value = Unit
    }

    override fun getAllNote() {
        progressState.value=true
        repository.getAllNotes().onEach {
            getAllNote.value=it
            progressState.value=false
        }.launchIn(viewModelScope)
    }
    override fun addUrl() {
        openUrlDialog.value=Unit
    }
}