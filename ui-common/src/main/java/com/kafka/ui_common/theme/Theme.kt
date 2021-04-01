package com.kafka.ui_common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import com.kafka.ui_common.theme.KafkaColorsDark
import com.kafka.ui_common.theme.KafkaColorsLight

@Composable
fun SimpleTheme(
    useDarkColors: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (useDarkColors) KafkaColorsDark else KafkaColorsLight,
        typography = Type
    ) {
        CompositionLocalProvider(LocalRippleTheme provides RekhtaRippleTheme) {
            content()
        }
    }
}

@Immutable
object RekhtaRippleTheme : RippleTheme {
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
