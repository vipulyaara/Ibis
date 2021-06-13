package com.notes.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding

@ExperimentalComposeUiApi
@ExperimentalAnimatedInsets
@Composable
fun EditorScreen(
    textFieldValue: TextFieldValue,
    setTextFieldValue: (TextFieldValue) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Editor(
            textFieldValue, setTextFieldValue,
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
//        TextActions {
//
//        }
    }
}
