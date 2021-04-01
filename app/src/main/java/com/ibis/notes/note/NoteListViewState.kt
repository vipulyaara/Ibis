package com.ibis.notes.note

import com.kafka.ui_common.BaseViewState
import org.rekhta.data.entities.Note

data class NoteListViewState(
    val notes: List<Note>? = null,
    val isLoading: Boolean = false
): BaseViewState
