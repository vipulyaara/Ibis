package com.ibis.note.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kafka.ui_common.ReduxViewModel
import com.notes.domain.interactors.UpdateNote
import com.notes.domain.observers.ObserveNoteDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import org.rekhta.data.debug
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val updateNote: UpdateNote,
    savedStateHandle: SavedStateHandle
) : ReduxViewModel<CreateNoteState>(CreateNoteState()) {

    init {
        val noteId = UUID.randomUUID().toString()
        viewModelScope.launchSetState { copy(noteId = noteId) }
    }

    fun saveEditorState(title: String, text: String) {
        debug { "save state $title $text" }
        updateNote(UpdateNote.Params(currentState.noteId!!, title, text))
    }
}

data class CreateNoteState(val noteId: String? = null)
