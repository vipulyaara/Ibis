package com.ibis.ui.search

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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    observeNoteDetail: ObserveNoteDetail,
    private val updateNote: UpdateNote,
    savedStateHandle: SavedStateHandle
) : ReduxViewModel<SearchViewState>(SearchViewState()) {

    init {
        viewModelScoped {
            observeNoteDetail.observe().distinctUntilChanged().collect {
                it.ifData {
                    viewModelScope.launchSetState { copy(note = it) }
                }
            }
        }
    }
}
