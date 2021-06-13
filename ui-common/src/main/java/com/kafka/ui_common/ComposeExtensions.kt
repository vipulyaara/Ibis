package com.kafka.ui_common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

fun TextStyle.alignCenter() = merge(TextStyle(textAlign = TextAlign.Center))

operator fun TextUnit.plus(other: TextUnit) = (value + other.value).sp

operator fun TextUnit.minus(other: TextUnit) = (value - other.value).sp

@Composable
inline fun <T> rememberMutableState(key: Any? = null, init: @DisallowComposableCalls () -> T) = remember(key) { mutableStateOf(init()) }

@Composable
inline fun <T> rememberSavableMutableState(
    key: Any? = null,
    saver: Saver<T, out Any> = autoSaver(),
    crossinline init: @DisallowComposableCalls () -> T,
) = rememberSaveable(key, saver) { mutableStateOf(init()) }
