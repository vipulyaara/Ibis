package com.ibis.note.detail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kafka.ui_common.Screen
import com.kafka.ui_common.navigateTo
import java.util.*

@Composable
fun CreateNote(navController: NavController) {
    val noteId = UUID.randomUUID().toString()

    navController.navigateTo(Screen.NoteDetail(noteId))
}
