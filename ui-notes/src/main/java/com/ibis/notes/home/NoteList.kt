package com.ibis.notes.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.kafka.ui_common.Screen
import com.kafka.ui_common.navigateTo
import com.kafka.ui_common.widgets.AutoSizedCircularProgressIndicator
import com.kafka.ui_common.widgets.FullScreenMessage
import org.rekhta.data.entities.Note

@ExperimentalAnimationApi
@Composable
fun NoteList(navController: NavController) {
    val noteListViewModel: NoteListViewModel = hiltNavGraphViewModel()
    val noteListViewState by noteListViewModel.state.collectAsState()

    val topBar: @Composable () -> Unit = { TopBar {} }

    Scaffold(
        topBar = { topBar() },
        floatingActionButton = {
            CreateNoteFab {
                navController.navigateTo(Screen.CreateNote)
            }
        }
    ) {
        noteListViewState.notes?.let {
            if (it.isEmpty()) {
                EmptyNotes()
            } else {
                NotesColumn(it) { navController.navigateTo(Screen.NoteDetail(it)) }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun NotesColumn(list: List<Note>, onNoteClicked: (String) -> Unit) {
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
private fun TopBar(onProfileClicked: () -> Unit) {
    TopAppBar(
        title = {},
        backgroundColor = MaterialTheme.colors.background,
        elevation = 1.dp,
        actions = {
            AutoSizedCircularProgressIndicator(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
            )

            IconButton(modifier = Modifier.padding(14.dp), onClick = onProfileClicked) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    )
}
