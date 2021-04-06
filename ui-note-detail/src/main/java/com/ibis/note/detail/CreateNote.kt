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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.kafka.ui_common.Screen
import com.kafka.ui_common.navigateTo
import com.notes.editor.EditorScreen
import com.notes.editor.rememberMutableState
import java.util.*

@ExperimentalAnimatedInsets
@ExperimentalComposeUiApi
@Composable
fun CreateNote(navController: NavController) {
    val createNoteViewModel: CreateViewModel = hiltNavGraphViewModel()
    val createNoteViewState by createNoteViewModel.state.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        keyboardController?.showSoftwareKeyboard()
    }

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        createNoteViewState.noteId?.let {
            val (titleFieldValue, setTitleFieldValue) = rememberMutableState {
                TextFieldValue(text = "")
            }

            val (textFieldValue, setTextFieldValue) = rememberMutableState {
                TextFieldValue(text = "")
            }

            Scaffold(
                topBar = {
                    TopBar {
                        createNoteViewModel.saveEditorState(titleFieldValue.text, textFieldValue.text)
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
