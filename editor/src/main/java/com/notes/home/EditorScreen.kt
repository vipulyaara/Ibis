package com.notes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.imePadding

@ExperimentalComposeUiApi
@ExperimentalAnimatedInsets
@Composable
fun EditorScreen(
    titleFieldValue: TextFieldValue,
    setTitleFieldValue: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    setTextFieldValue: (TextFieldValue) -> Unit
) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//    DisposableEffect(Unit) {
//        keyboardController?.showSoftwareKeyboard()
//        onDispose {  }
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .imePadding()
    ) {
        NoteTitle(titleFieldValue, setTitleFieldValue, Modifier)
        Editor(
            textFieldValue, setTextFieldValue,
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        TextActions {

        }
    }
}
