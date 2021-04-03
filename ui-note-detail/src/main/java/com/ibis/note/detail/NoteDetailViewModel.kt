package com.ibis.note.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kafka.ui_common.ReduxViewModel
import com.kafka.ui_common.viewModelScoped
import com.notes.domain.interactors.UpdateNote
import com.notes.domain.observers.ObserveNoteDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import org.ibis.base.ifData
import org.rekhta.data.debug
import org.rekhta.data.entities.Note
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    observeNoteDetail: ObserveNoteDetail,
    private val updateNote: UpdateNote,
    savedStateHandle: SavedStateHandle
) : ReduxViewModel<NoteDetailViewState>(NoteDetailViewState()) {

    init {
        viewModelScoped {
            observeNoteDetail.observe().distinctUntilChanged().collect {
                it.ifData {
                    viewModelScope.launchSetState { copy(note = it) }
                }
            }
        }

        var noteId = savedStateHandle.get<String>("note_id")!!

        if (noteId == "create") {
            viewModelScope.launchSetState { copy(createNote = true) }
            noteId = UUID.randomUUID().toString()
            viewModelScope.launchSetState { copy(note = Note(noteId)) }
        } else {
            debug { "observing note id $noteId" }
            observeNoteDetail(noteId)
        }
    }

    fun saveEditorState(title: String? = null, text: String? = null) {
        debug { "saveEditorState ${currentState.note?.id} $title $text" }
        val noteId = currentState.note!!.id
        val noteTitle = title ?: currentState.note?.title.orEmpty()
        val noteText = text ?: currentState.note?.text.orEmpty()

        val newTitle = if (noteTitle.isBlank()) {
            noteText.split("\n").firstOrNull().orEmpty()
        } else {
            noteTitle
        }

        updateNote(UpdateNote.Params(noteId, newTitle, noteText))
    }
}
