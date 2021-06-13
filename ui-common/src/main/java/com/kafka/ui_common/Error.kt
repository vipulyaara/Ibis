package com.kafka.ui_common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ErrorSnackbar(snackbarError: UiError?, scaffoldState: ScaffoldState, show: Boolean = true) {
    val scope = rememberCoroutineScope()
    if (show && snackbarError != null) {
        SideEffect {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(snackbarError.message)
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun FullScreenError(
    show: Boolean = true,
    uiError: UiError?
) {
    AnimatedVisibility(visible = show) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Image(
                painter = painterResource(id = R.drawable.img_absurd_error),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.h6.alignCenter()
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = uiError?.message.orEmpty(),
                style = MaterialTheme.typography.body2.alignCenter()
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun PaginatedError(
    modifier: Modifier = Modifier,
    show: Boolean = true,
    message: String,
    onRetry: () -> Unit = {}
) {
    AnimatedVisibility(visible = show, enter = fadeIn(), exit = fadeOut()) {
        Surface(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colors.error) {
            Row(
                modifier = modifier
                    .clickable(onClick = onRetry)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.plus("\n\nClick to retry"),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
