package com.azamovhudstc.noteappplaystore.viewmodel.add

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
class AddNoteViewModelImpl @Inject constructor(var repository: AppRepository):AddNoteViewModel,ViewModel() {
    override val addNoteViewModel: MutableLiveData<Unit> = MutableLiveData()
    override fun addNote(noteEntity: NoteEntity) {
        repository.addNote(noteEntity).onEach {
            if (it=="Success"){
                addNoteViewModel.value=Unit
            }
        }.launchIn(viewModelScope)
    }
}