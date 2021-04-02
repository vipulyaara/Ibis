package com.notes.editor

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.google.accompanist.insets.ExperimentalAnimatedInsets

@ExperimentalAnimatedInsets
@Composable
fun Editor(
    textFieldValue: TextFieldValue,
    setTextFieldValue: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    EditorTextField(
        textFieldValue,
        { setTextFieldValue(it) },
        modifier,
    )
}

@Composable
fun EditorTextField(
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
                text = "Start typing...",
                style = MaterialTheme.typography.body1
            )
        },
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            unfocusedIndicatorColor = MaterialTheme.colors.background,
            focusedIndicatorColor = MaterialTheme.colors.background
        ),
    )
}

@Composable
inline fun <T> rememberMutableState(key: Any? = null, init: @DisallowComposableCalls () -> T) =
    remember(key) { mutableStateOf(init()) }
