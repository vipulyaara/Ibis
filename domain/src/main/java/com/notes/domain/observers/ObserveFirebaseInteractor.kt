package com.notes.domain.observers

import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.ibis.base.InvokeResponse
import org.ibis.base.ResponseOrigin
import org.ibis.base.SubjectInteractor
import org.rekhta.data.debug

abstract class ObserveFirebaseInteractor<P : Any, T> : SubjectInteractor<P, InvokeResponse<T>>() {

    abstract suspend fun createResponseObservable(params: P): Flow<T>

    override suspend fun createObservable(params: P): Flow<InvokeResponse<T>> =
        createResponseObservable(params).map {
            InvokeResponse.Data(it, ResponseOrigin.Fetcher)
        }.catch {
            error("firebase failed")
        }.flowOn(Dispatchers.IO)

    fun Query.snapshots(): Flow<Pair<QuerySnapshot?, FirebaseFirestoreException?>> {
        return callbackFlow {
            val listener = addSnapshotListener { querySnapshot, exception ->
                offer(querySnapshot to exception)
            }
            awaitClose {
                listener.remove()
                debug { "Remove listener $listener for query ${this@snapshots}" }
            }
        }
    }

    fun DocumentReference.snapshots(): Flow<Pair<DocumentSnapshot?, FirebaseFirestoreException?>> {
        return callbackFlow {
            val listener = addSnapshotListener { querySnapshot, exception ->
                offer(querySnapshot to exception)
            }
            awaitClose {
                listener.remove()
                debug { "Remove listener $listener for query ${this@snapshots}" }
            }
        }
    }
}
