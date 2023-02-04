package com.azamovhudstc.noteappplaystore.repo.imp

import com.azamovhudstc.noteappplaystore.data.local.dao.NoteDao
import com.azamovhudstc.noteappplaystore.data.local.models.NoteEntity
import com.azamovhudstc.noteappplaystore.repo.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepositoryImp @Inject constructor(private val noteDatabaseDao: NoteDao) : AppRepository {
    override fun deleteNote(note: NoteEntity): Flow<String> = flow {
        noteDatabaseDao.deleteNote(note)
        emit("Deleted")
    }


    override fun addNote(note: NoteEntity): Flow<String> = flow {
        noteDatabaseDao.insertNote(note)
        emit("Success")
    }.flowOn(Dispatchers.IO)

    override fun editNote(note: NoteEntity)= flow<String>{
        noteDatabaseDao.editNote(note)
        emit("Edited")

    }

    override fun getAllNotes(): Flow<List<NoteEntity>> = flow {
        emit(noteDatabaseDao.getNotes())
    }.flowOn(Dispatchers.IO)
}