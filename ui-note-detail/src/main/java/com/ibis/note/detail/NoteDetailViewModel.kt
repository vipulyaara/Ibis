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

        val noteId = savedStateHandle.get<String>("note_id")!!

        debug { "observing note id $noteId" }
        observeNoteDetail(noteId)
    }

    fun saveEditorState(title: String, text: String) {
        debug { "saveEditorState ${currentState.note?.id} $title $text" }
        updateNote(UpdateNote.Params(currentState.note!!.id, "Unknown Title", text.trim()))
    }
}
