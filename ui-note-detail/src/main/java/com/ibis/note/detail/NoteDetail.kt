package com.ibis.note.detail

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.notes.editor.EditorScreen
import com.notes.editor.rememberMutableState
import compose.icons.TablerIcons
import compose.icons.tablericons.Check

@ExperimentalComposeUiApi
@ExperimentalAnimatedInsets
@Composable
fun NoteDetail() {
    val noteViewModel: NoteDetailViewModel = hiltNavGraphViewModel()
    val noteViewState by noteViewModel.state.collectAsState()

    val title = noteViewState.note?.title.orEmpty()
    val text = noteViewState.note?.text.orEmpty()

    val backPressDispatcher = LocalOnBackPressedDispatcherOwner.current.onBackPressedDispatcher

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        noteViewState.note?.let {
            val (titleFieldValue, setTitleFieldValue) = rememberMutableState {
                TextFieldValue(
                    text = noteViewState.note?.title.orEmpty(),
                    selection = TextRange(title.length)
                )
            }

            val (textFieldValue, setTextFieldValue) = rememberMutableState {
                TextFieldValue(
                    text = noteViewState.note?.text.orEmpty(),
                    selection = TextRange(text.length)
                )
            }

            Scaffold(
                topBar = {
                    TopBar {
                        noteViewModel.saveEditorState(titleFieldValue.text, textFieldValue.text)
                        backPressDispatcher.onBackPressed()
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            ) {
                EditorScreen(
                    titleFieldValue,
                    setTitleFieldValue,
                    textFieldValue,
                    setTextFieldValue
                )
            }
        }
    }
}

@Composable
private fun TopBar(onDoneClicked: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .clickable { onDoneClicked() }
                    .size(56.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = TablerIcons.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
