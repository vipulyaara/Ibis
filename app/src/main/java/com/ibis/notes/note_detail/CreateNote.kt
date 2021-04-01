package com.ibis.notes.note_detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ibis.notes.Screen
import com.ibis.notes.navigateTo
import java.util.*

@Composable
fun CreateNote(navController: NavController) {
    val noteId = UUID.randomUUID().toString()

    navController.navigateTo(Screen.NoteDetail(noteId))
}
