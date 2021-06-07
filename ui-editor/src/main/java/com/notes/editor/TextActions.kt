package com.notes.editor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.twotone.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kafka.ui_common.theme.iconPrimary

@Composable
fun TextActions(onIconClick: (TextAction) -> Unit) {
    Surface(color = MaterialTheme.colors.background, elevation = 0.dp) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val modifier = Modifier
                .weight(1f)
                .size(56.dp)

            TextAction.values().forEach {
                Box(modifier = modifier.clickable { onIconClick(it) }) {
                    IconAction(modifier = Modifier.align(Alignment.Center), textAction = it)
                }
            }
        }
    }
}

@Composable
fun IconAction(modifier: Modifier, textAction: TextAction) {
    Icon(
        imageVector = textAction.imageVector,
        contentDescription = null,
        modifier = modifier,
        tint = MaterialTheme.colors.iconPrimary
    )
}

enum class TextAction(val imageVector: ImageVector) {
    Bold(Icons.Default.Edit),
//    Italic(Icons.Default.Build),
    Code(Icons.Default.Notifications),
    TextSize(Icons.Default.Person),
    Color(Icons.Default.LocationOn),
//    Link(Icons.Default.MailOutline),
    Ambulance(Icons.Default.Call),
}
