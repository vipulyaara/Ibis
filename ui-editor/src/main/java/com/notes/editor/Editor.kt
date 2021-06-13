package com.notes.editor

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.kafka.ui_common.theme.textPrimary

@ExperimentalAnimatedInsets
@Composable
fun Editor(
    textFieldValue: TextFieldValue,
    setTextFieldValue: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedString = remember(textFieldValue.annotatedString) {
        buildAnnotatedString {
            append("\n${textFieldValue.annotatedString}")
        }
    }
    EditorTextField(
        annotatedString,
        { setTextFieldValue(TextFieldValue(annotatedString, TextRange(annotatedString.length))) },
        modifier
    )
}

@Composable
private fun EditorTextField(
    textState: AnnotatedString,
    setState: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        modifier = modifier,
        value = textState.text,
        onValueChange = { setState(it) },
        textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.textPrimary),
    )
}
