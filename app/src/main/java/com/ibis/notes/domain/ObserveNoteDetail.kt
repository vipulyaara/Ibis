package com.ibis.notes.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.SubjectInteractor
import org.rekhta.data.daos.NotesDao
import org.rekhta.data.entities.Note
import javax.inject.Inject

class ObserveNoteDetail @Inject constructor(
    private val notesDao: NotesDao,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : SubjectInteractor<String, Note?>() {

    override suspend fun createObservable(params: String): Flow<Note?> {
        return notesDao.observeNoteById(params).flowOn(appCoroutineDispatchers.io)
    }
}
