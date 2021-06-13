package com.ibis.notes.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kafka.ui_common.Screen
import com.kafka.ui_common.navigateTo
import com.kafka.ui_common.widgets.FullScreenMessage
import org.rekhta.data.entities.Note

@ExperimentalAnimationApi
@Composable
fun NoteListScreen(navController: NavController) {
    val noteListViewModel: NoteListViewModel = hiltViewModel()
    val noteListViewState by noteListViewModel.state.collectAsState()

    val onProfileClicked = {
        if (noteListViewState.isUserLoggedIn) {
            navController.navigateTo(Screen.Profile)
        } else {
            navController.navigateTo(Screen.Login)
        }
    }

    Scaffold(
        topBar = { NoteListTopBar { onProfileClicked() } },
        floatingActionButton = {
            CreateNoteFab { navController.navigateTo(Screen.CreateNote) }
        }
    ) {
        noteListViewState.notes?.let {
            if (it.isEmpty()) {
                EmptyNotes()
            } else {
                NotesColumn(navController, it, noteListViewState.isUserLoggedIn) {
                    navController.navigateTo(Screen.NoteDetail(it))
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun NotesColumn(
    navController: NavController,
    list: List<Note>,
    isUserLoggedIn: Boolean,
    onNoteClicked: (String) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(top = 12.dp)) {
        list.forEachIndexed { index, note ->
            if (index.coerceAtMost(list.size) == 1 && !isUserLoggedIn) {
                LoginWidget(modifier = Modifier.padding(12.dp)) {
                    navController.navigateTo(Screen.Login)
                }
            }
            NoteItem(note = note) { onNoteClicked(it) }
        }
    }
}

// performance of LazyColumn is still degraded so use NotesColumn
@ExperimentalAnimationApi
@Composable
private fun NotesLazyColumn(list: List<Note>, onNoteClicked: (String) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)) {
        items(list) {
            NoteItem(note = it) { onNoteClicked(it) }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun EmptyNotes() {
    FullScreenMessage(title = "No notes here", message = "Create notes from below to see them here")
}

@Composable
private fun CreateNoteFab(onClick: () -> Unit) {
    FloatingActionButton(modifier = Modifier.padding(12.dp), onClick = onClick) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
}

@Composable
private fun NoteListTopBar(onProfileClicked: () -> Unit) {
    TopAppBar(
        title = {},
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp,
        actions = {
            IconButton(modifier = Modifier.padding(14.dp), onClick = onProfileClicked) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    )
}
