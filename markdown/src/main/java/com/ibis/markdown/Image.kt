package com.ibis.markdown

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Image(imageVector: ImageVector, modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = null
    )
}
