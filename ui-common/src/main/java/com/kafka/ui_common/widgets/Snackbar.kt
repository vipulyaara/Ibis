package com.kafka.ui_common.widgets

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Wrapper around [Snackbar] to make it swipe-dismissable,
 * using [SwipeToDismiss].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeDismissSnackbar(
    data: SnackbarData,
    onDismiss: (() -> Unit)? = null,
    snackbar: @Composable (SnackbarData) -> Unit = {
        Snackbar(
            snackbarData = it,
            shape = RectangleShape,
            contentColor = MaterialTheme.colors.onError,
            backgroundColor = MaterialTheme.colors.error,
            elevation = 0.dp
        )
    }
) {
    val dismissState = rememberDismissState {
        if (it != DismissValue.Default) {
            data.dismiss()
            onDismiss?.invoke()
        }
        true
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        background = {},
        dismissContent = { snackbar(data) }
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun Snackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = SnackbarDefaults.backgroundColor,
    contentColor: Color = MaterialTheme.colors.surface,
    actionColor: Color = SnackbarDefaults.primaryActionColor,
    elevation: Dp = 6.dp
) {
    val actionLabel = snackbarData.actionLabel
    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = actionColor),
                onClick = { snackbarData.performAction() },
                content = { Text(actionLabel) }
            )
        }
    } else {
        null
    }

    Snackbar(
        modifier = modifier,
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    ) {
        Text(snackbarData?.message.orEmpty())
    }
}
