package com.ibis.notes.note

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.ibis.notes.Screen
import com.ibis.notes.navigateTo
import com.ibis.notes.widgets.FullScreenMessage
import compose.icons.TablerIcons
import compose.icons.tablericons.Edit
import org.rekhta.data.entities.Note

@ExperimentalAnimationApi
@Composable
fun NoteList(navController: NavController) {
    val noteListViewModel: NoteListViewModel = hiltNavGraphViewModel()
    val noteListViewState by noteListViewModel.state.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = {
            CreateNoteFab {
                navController.navigateTo(Screen.NoteDetail("create"))
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
        Icon(imageVector = TablerIcons.Edit, contentDescription = null)
    }
}

@Composable
private fun TopBar() {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 1.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                strokeWidth = 2.dp
            )
        }
    }
}
