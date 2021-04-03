package com.kafka.ui_common.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import kotlin.math.max
import kotlin.math.min

val brandRed = Color(0xffff006a)
val brandBlue = Color(0xff24aaff)
val white = Color(0xFFFFFFFF)
val whiteCream = Color(0xFFFAFAFA)
val brandBlue100 = Color(0xffDFE9FF)
val brandBlue200 = Color(0xbbCCD7F0)
val brandBlue600 = Color(0xff7B8994)

val darkGrey600 = Color(0xff212121)
val darkGrey700 = Color(0xff1A1B1C)
val darkGrey800 = Color(0xff121212)
val darkGrey900 = Color(0xFF050F09)
val textSecondaryDark = Color(0xff888888)

val error = Color(0xffBA0550)

val Colors.divider
    @Composable get() = if (isLight) brandBlue200 else darkGrey600

val Colors.dividerVariant
    @Composable get() = if (isLight) darkGrey800 else whiteCream

val Colors.textPrimary
    @Composable get() = if (isLight) darkGrey900 else whiteCream

val Colors.textSecondary
    @Composable get() = if (isLight) brandBlue600 else textSecondaryDark

val Colors.iconPrimary
    @Composable get() = if (isLight) darkGrey800 else whiteCream

val Colors.yellow
    @Composable get() = Color(0xfff6bf26)

val Colors.green
    @Composable get() = Color(0xfff6bf26)

val Colors.black
    @Composable get() = Color(0xff000000)

val Colors.white
    @Composable get() = Color(0xffffffff)

val Colors.blue
    @Composable get() = Color(0xff3D84FD)

val Colors.yellowDark
    @Composable get() = Color(0xffFFBC0D)

val Colors.greenDark
    @Composable get() = GreenDark

private val Slate200 = Color(0xFF81A9B3)
private val Slate600 = Color(0xFF4A6572)
private val Slate800 = Color(0xFF232F34)

private val Orange500 = brandRed
private val Orange700 = Color(0xFFC78522)
private val Orange800 = Color(0xFFFFC2AC)
private val GreenDark = Color(0xFF295242)

val KafkaColorsLight = lightColors(
    primary = GreenDark,
    primaryVariant = darkGrey800,
    secondary = GreenDark,
    secondaryVariant = darkGrey900,
    background = white,
    surface = brandBlue100,
    error = error,
    onPrimary = white,
    onSecondary = white,
    onBackground = darkGrey800,
    onSurface = darkGrey800,
    onError = white
)

val KafkaColorsDark = darkColors(
    primary = Orange800,
    primaryVariant = white,
    secondary = Orange800,
    background = darkGrey900,
    surface = darkGrey800,
    error = error,
    onBackground = whiteCream,
    onSurface = whiteCream,
    onError = white
).withBrandedSurface()

fun Colors.withBrandedSurface() = copy(
    surface = primary.copy(alpha = 0.08f).compositeOver(this.surface),
)

fun Color.contrastAgainst(background: Color): Float {
    val fg = if (alpha < 1f) compositeOver(background) else this

    val fgLuminance = fg.luminance() + 0.05f
    val bgLuminance = background.luminance() + 0.05f

    return max(fgLuminance, bgLuminance) / min(fgLuminance, bgLuminance)
}
