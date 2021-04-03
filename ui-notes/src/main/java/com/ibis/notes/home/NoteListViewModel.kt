package com.ibis.notes.home

import androidx.lifecycle.viewModelScope
import com.kafka.ui_common.ReduxViewModel
import com.kafka.ui_common.viewModelScoped
import com.notes.domain.interactors.UpdateNote
import com.notes.domain.observers.ObserveNoteList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import org.ibis.base.InvokeResponse
import org.rekhta.data.debug
import org.rekhta.data.entities.Note
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    observeNoteList: ObserveNoteList,
    private val updateNote: UpdateNote
) : ReduxViewModel<NoteListViewState>(NoteListViewState()) {

    init {
        viewModelScoped {
            observeNoteList.observe().collect {
                debug { "notes are $it" }
                if (it is InvokeResponse.Data) {
                    viewModelScope.launchSetState { copy(notes = it.dataOrNull()) }
                }

//                it.throwIfError()
            }
        }

        observeNoteList(Unit)
    }

    private fun insertFakeNotes() {
        notes.forEach {
            updateNote(UpdateNote.Params(it.id, it.title, it.text))
        }
    }
}

val notes = listOf(
    Note("1", "Ibn-e-maryam hua kare koi", "Ibn-e-maryam hua kare koi\nmere dukh ki dawa kare koi"),
    Note(
        "4",
        "Meer ham milke bahot khush hue tumse pyaare",
        "ye ra.ng be-ra.ng saare manzar hai.n ek jaise\n" +
                "ye phuul saare ye saare nashtar hai.n ek jaise"
    ),
    Note(
        "3",
        "Ham hue tum hue k meer hue",
        "jaise KHalaa ke pas-manzar me.n ra.ng ra.ng ke naqsh-o-nigaar\n" +
                "baate.n us kii vazn se KHaalii lahja bhaarii-bharkam hai"
    ),
    Note(
        "2",
        "Ibn-e-maryam hua kare koi",
        "Compid is an app helping you with your daily taks , organization and taking notes.\n" +
                "The symbol is a combination of \" Notes/Papers\" and \"Check mark\". Let me know your thoughts!"
    ),
)
