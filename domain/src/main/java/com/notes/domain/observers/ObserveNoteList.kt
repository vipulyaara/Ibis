package com.notes.domain.observers

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.notes.domain.FirebaseConstants.collection_notes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.rekhta.data.entities.Note
import javax.inject.Inject

class ObserveNoteList @Inject constructor() : ObserveFirebaseInteractor<Unit, List<Note>>() {
    private val notesCollection = FirebaseFirestore.getInstance().collection(collection_notes)

    override suspend fun createResponseObservable(params: Unit): Flow<List<Note>> =
        notesCollection
            .orderBy("lastUpdated", Query.Direction.DESCENDING)
            .snapshots()
            .map { (querySnapshot, exception) ->
                exception?.let { throw it }
                querySnapshot?.toObjects(Note::class.java)!!
            }
}
