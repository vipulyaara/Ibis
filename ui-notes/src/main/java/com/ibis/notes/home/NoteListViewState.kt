package com.ibis.notes.home

import com.kafka.ui_common.BaseViewState
import org.rekhta.data.entities.Note

data class NoteListViewState(
    val notes: List<Note>? = null,
    val isUserLoggedIn: Boolean = false,
    val isLoading: Boolean = false
): BaseViewState
