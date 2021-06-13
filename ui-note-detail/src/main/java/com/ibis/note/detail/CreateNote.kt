package com.ibis.note.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.notes.editor.EditorScreen
import com.notes.editor.rememberMutableState

@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@Composable
fun CreateNote(navController: NavController) {
    val createNoteViewModel: CreateViewModel = hiltViewModel()
    val createNoteViewState by createNoteViewModel.state.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        keyboardController?.show()
    }

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        val (titleFieldValue, setTitleFieldValue) = rememberMutableState { TextFieldValue(text = "") }
        val (textFieldValue, setTextFieldValue) = rememberMutableState { TextFieldValue(text = "\n\n") }

        Scaffold(
            topBar = {
                TopBar {
                    createNoteViewModel.saveEditorState(titleFieldValue.text, textFieldValue.text)
                }
            },
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            EditorScreen(textFieldValue, setTextFieldValue)
        }
    }
}
