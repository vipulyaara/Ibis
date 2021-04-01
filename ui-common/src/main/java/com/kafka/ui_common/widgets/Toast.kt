package com.kafka.ui_common.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toast(message: String) {
    android.widget.Toast.makeText(LocalContext.current, message, android.widget.Toast.LENGTH_SHORT)
        .show()
}
