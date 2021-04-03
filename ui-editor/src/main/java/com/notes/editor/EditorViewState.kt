package com.notes.editor

import com.kafka.ui_common.BaseViewState

data class EditorViewState(val title: String = "", val text: String = "") : BaseViewState
