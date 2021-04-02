package com.notes.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.google.accompanist.insets.ExperimentalAnimatedInsets

@ExperimentalAnimatedInsets
@Composable
fun NoteTitle(
    titleFieldValue: TextFieldValue,
    setTitleFieldValue: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    TitleTextField(titleFieldValue, setTitleFieldValue, modifier.fillMaxWidth())
}

@Composable
fun TitleTextField(
    textState: TextFieldValue,
    setState: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        value = textState,
        onValueChange = { setState(it) },
        placeholder = {
            Text(
                text = "Title",
                style = MaterialTheme.typography.h6
            )
        },
        textStyle = MaterialTheme.typography.h6,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            unfocusedIndicatorColor = MaterialTheme.colors.background,
            focusedIndicatorColor = MaterialTheme.colors.background
        ),
    )
}
