package com.notes.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
inline fun <T> rememberMutableState(key: Any? = null, init: @DisallowComposableCalls () -> T) =
    remember(key) { mutableStateOf(init()) }
