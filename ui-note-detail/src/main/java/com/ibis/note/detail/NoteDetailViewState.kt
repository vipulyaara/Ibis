package com.ibis.note.detail

import com.kafka.ui_common.BaseViewState
import org.rekhta.data.entities.Note

data class NoteDetailViewState(val note: Note? = null) : BaseViewState
