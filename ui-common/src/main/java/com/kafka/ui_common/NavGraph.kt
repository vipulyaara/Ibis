package com.kafka.ui_common

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.navigate

const val ROUTE_NOTES_LIST = "notes"
const val ROUTE_NOTE_DETAIL = "note/{note_id}"

sealed class Screen(val route: String) {
    object NotesList : Screen(ROUTE_NOTES_LIST)
    class NoteDetail(val noteId: String) : Screen(ROUTE_NOTE_DETAIL)
}

fun NavController.navigateTo(screen: Screen, builder: NavOptionsBuilder.() -> Unit = {}) {
    when (screen) {
        is Screen.NoteDetail -> navigate("note/${screen.noteId}", builder)
        else -> navigate(screen.route, builder)
    }
}
