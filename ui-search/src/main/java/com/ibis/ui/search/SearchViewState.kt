package com.ibis.ui.search

import com.kafka.ui_common.BaseViewState
import org.rekhta.data.entities.Note

data class SearchViewState(val note: Note? = null) : BaseViewState
