package com.notes.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.kafka.ui_common.theme.yellow

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
        modifier
    )
}

@Composable
fun EditorTextField(
    textState: TextFieldValue,
    setState: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        modifier = modifier.padding(top = 16.dp),
        value = textState,
        onValueChange = { setState(it) },
        textStyle = MaterialTheme.typography.body1,
    )
}

@Composable
inline fun <T> rememberMutableState(key: Any? = null, init: @DisallowComposableCalls () -> T) =
    remember(key) { mutableStateOf(init()) }
