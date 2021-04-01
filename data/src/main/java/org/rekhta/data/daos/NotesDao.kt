package org.rekhta.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.rekhta.data.entities.Note

@Dao
abstract class NotesDao : EntityDao<Note>() {
    @Transaction
    @Query("SELECT * FROM note ORDER BY last_updated DESC")
    abstract fun observeAllNotes(): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM note where note_id = :id")
    abstract fun observeNoteById(id: String): Flow<Note>

    @Query("DELETE FROM note WHERE note_id = :noteId")
    abstract suspend fun delete(noteId: String)

    @Query("DELETE FROM note")
    abstract suspend fun deleteAll()
}
