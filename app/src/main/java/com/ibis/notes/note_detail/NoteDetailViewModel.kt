package com.ibis.notes.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kafka.ui_common.ReduxViewModel
import com.kafka.ui_common.viewModelScoped
import com.notes.domain.observers.ObserveNoteDetail
import com.notes.domain.interactors.UpdateNote
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
    private var noteId: String

    init {
        viewModelScoped {
            observeNoteDetail.observe().distinctUntilChanged().collect {
                it.ifData {
                    viewModelScope.launchSetState { copy(note = it) }
                }
            }
        }

        noteId = savedStateHandle.get<String>("note_id")!!

        if (noteId == "create") {
            noteId = UUID.randomUUID().toString()
            viewModelScope.launchSetState { copy(note = Note(noteId)) }
        }

        debug { "observing note id $noteId" }
        observeNoteDetail(noteId)
    }

    fun saveEditorState(title: String? = null, text: String? = null) {
        debug { "saveEditorState $noteId $title $text" }
        val noteId = currentState.note!!.id
        val noteTitle = title ?: currentState.note?.title.orEmpty()
        val noteText = text ?: currentState.note?.text.orEmpty()

        updateNote(UpdateNote.Params(noteId, noteTitle, noteText))
    }
}
