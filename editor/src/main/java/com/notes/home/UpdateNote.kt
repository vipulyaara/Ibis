package com.notes.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.SuspendInteractor
import org.rekhta.data.daos.NotesDao
import org.rekhta.data.debug
import org.rekhta.data.entities.Note
import org.rekhta.data.injection.ProcessLifetime
import javax.inject.Inject

class UpdateNote @Inject constructor(
    private val notesDao: NotesDao,
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    @ProcessLifetime override val scope: CoroutineScope
) : SuspendInteractor<UpdateNote.Params>() {

    override suspend fun doWork(params: Params) {
        scope.launch(appCoroutineDispatchers.io) {
            params.apply {
                debug { "inserting note $params" }
                if (title.isNotEmpty() && text.isNotEmpty()) {
                    val note = Note(noteId, title, text, System.currentTimeMillis())
                    notesDao.insert(note)
                }
            }
        }
    }

    data class Params(val noteId: String, val title: String = "", val text: String = "")
}
