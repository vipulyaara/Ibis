package com.notes.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kafka.ui_common.theme.iconPrimary
import compose.icons.TablerIcons
import compose.icons.tablericons.*

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
    Bold(TablerIcons.Bold),
    Italic(TablerIcons.Italic),
    Code(TablerIcons.Code),
    TextSize(TablerIcons.TextWrap),
    Color(TablerIcons.ColorSwatch),
    Link(TablerIcons.ExternalLink),
    Ambulance(TablerIcons.BellRinging2),
}
