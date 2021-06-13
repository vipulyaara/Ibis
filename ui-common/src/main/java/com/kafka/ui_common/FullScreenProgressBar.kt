package com.kafka.ui_common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kafka.ui_common.theme.iconPrimary
import org.rekhta.data.debug

@ExperimentalAnimationApi
@Composable
fun FullScreenProgressBar(modifier: Modifier = Modifier, show: Boolean = true) {
    var isVisible by rememberMutableState(init = { false })
    LaunchedEffect(show) { isVisible = show }

    if (isVisible) {
        debug { "show loading $isVisible" }
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .zIndex(2f)
        ) {
            Image(
                modifier = Modifier
                    .size(76.dp)
                    .align(Alignment.Center),
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_ibis),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.iconPrimary)
            )
        }
    }
}
