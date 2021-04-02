package com.notes.editor

import androidx.lifecycle.SavedStateHandle
import com.kafka.ui_common.ReduxViewModel
import com.kafka.ui_common.viewModelScoped
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val updateNote: UpdateNote,
    private val savedStateHandle: SavedStateHandle
) : ReduxViewModel<EditorViewState>(EditorViewState()) {

    fun saveEditorState() {
        val noteId = savedStateHandle.get<String>("note_id")!!

        viewModelScoped {
            updateNote(UpdateNote.Params(noteId, state.value.title, state.value.text))
        }
    }
}
