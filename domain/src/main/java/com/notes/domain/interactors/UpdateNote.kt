package com.notes.domain.interactors

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.notes.domain.FirebaseConstants.collection_notes
import kotlinx.coroutines.CoroutineScope
import org.ibis.base.SuspendInteractor
import org.rekhta.data.debug
import org.rekhta.data.entities.Note
import org.rekhta.data.injection.ProcessLifetime
import javax.inject.Inject

class UpdateNote @Inject constructor(
    @ProcessLifetime override val scope: CoroutineScope
) : SuspendInteractor<UpdateNote.Params>() {
    private val notesCollection = FirebaseFirestore.getInstance().collection(collection_notes)

    override suspend fun doWork(params: Params) {
        params.apply {
            if (title.isNotEmpty() || text.isNotEmpty()) {
                debug { "inserting note $params" }
                val note = Note(noteId, title, text, System.currentTimeMillis())
                notesCollection.document(params.noteId).set(note, SetOptions.merge())
            }
        }
    }

    data class Params(val noteId: String, val title: String = "", val text: String = "")
}
