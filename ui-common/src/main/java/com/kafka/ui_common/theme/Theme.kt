package com.kafka.ui_common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable

@Composable
fun IbisTheme(
    useDarkColors: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (useDarkColors) KafkaColorsDark else KafkaColorsLight,
        typography = Type
    ) {
        CompositionLocalProvider(LocalRippleTheme provides IbisRippleTheme) {
            content()
        }
    }
}

@Immutable
object IbisRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = MaterialTheme.colors.surface,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = MaterialTheme.colors.surface,
        lightTheme = MaterialTheme.colors.isLight
    )
}
