package com.notes.domain.observers

import com.google.firebase.firestore.FirebaseFirestore
import com.notes.domain.FirebaseConstants.collection_notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.rekhta.data.entities.Note
import javax.inject.Inject

class ObserveNoteDetail @Inject constructor() : ObserveFirebaseInteractor<String, Note>() {
    private val notesCollection = FirebaseFirestore.getInstance().collection(collection_notes)

    override suspend fun createResponseObservable(params: String): Flow<Note> =
        notesCollection.document(params)
            .snapshots()
            .map { (querySnapshot, exception) ->
                exception?.let { throw it }
                querySnapshot?.toObject(Note::class.java)!!
            }
}
