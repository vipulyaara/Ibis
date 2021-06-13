package com.notes.domain.interactors

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.notes.domain.FirebaseConstants.collection_notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ibis.base.AppCoroutineDispatchers
import org.ibis.base.SuspendInteractor
import org.rekhta.data.debug
import org.rekhta.data.entities.Note
import org.rekhta.data.injection.ProcessLifetime
import javax.inject.Inject

class UpdateNote @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    @ProcessLifetime override val scope: CoroutineScope
) : SuspendInteractor<UpdateNote.Params>() {
    private val notesCollection = FirebaseFirestore.getInstance().collection(collection_notes)

    override suspend fun doWork(params: Params) {
        withContext(appCoroutineDispatchers.io) {
            params.apply {
                if (title.isNotEmpty() || text.isNotEmpty()) {
                    debug { "inserting note $params" }
                    val updatedTitle = autoAddTitle(title, text)
                    val note = Note(noteId, updatedTitle, text, System.currentTimeMillis())
                    notesCollection.document(params.noteId).set(note, SetOptions.merge())
                }
            }
        }
    }

    private fun autoAddTitle(title: String, text: String) = if (title.isBlank()) {
        text.split("\n").firstOrNull().orEmpty()
    } else {
        title
    }

    data class Params(val noteId: String, val title: String = "", val text: String = "")
}
