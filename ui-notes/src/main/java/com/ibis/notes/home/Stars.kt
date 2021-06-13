package com.ibis.notes.home

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

fun Modifier.drawStars(
    starState: StarState,
    clip: Shape = CircleShape,
    backgroundColor: Color = Color.Black
) = composed {
    var time by remember { mutableStateOf(0L) }

    LaunchedEffect(Unit) {
        while(true) {
            withFrameMillis { millis ->
                time = millis
            }
        }
    }
    this.background(backgroundColor, clip)
        .onSizeChanged { starState.onSizeChange(it.toSize()) }
        .drawBehind {
            starState.stars.forEach { star ->
                with(star) { draw(time) }
            }
        }
}

class StarState(starCount: Int, private val color: Color) {
    var starCount: Int by mutableStateOf(starCount)
        private set
    var warp: Float by mutableStateOf(3f)
        private set
    private var layerSize: Size? by mutableStateOf(null)

    private val list = mutableStateOf(listOf<Star>())
    internal val stars: List<Star> by list

    internal fun onSizeChange(newSize: Size) {
        if (!newSize.isEmpty() && newSize != layerSize) {
            layerSize = newSize
            regenerateStars()
        } else if (newSize.isEmpty()){
            // we don't have any size, stop doing any work
            list.value = emptyList()
        }
    }

    fun changeWarp(newSpeed: Float) {
        warp = newSpeed.coerceIn(1f, 100f)
    }

    fun changeStarCount(newStarCount: Int) {
        starCount = newStarCount.coerceAtLeast(0)
        regenerateStars()
    }

    private fun regenerateStars() {
        if (list.value.size == starCount) {
            return
        }
        val localSize = layerSize ?: return
        val missing = starCount - list.value.size
        if (missing > 0) {
            val toAdd = mutableListOf<Star>()
            for (i in 0 until missing) {
                toAdd.add(Star(localSize, color,this))
            }
            list.value = list.value + toAdd
        } else {
            list.value = list.value.dropLast(-1 * missing)
        }
    }
}

internal class Star(
    size: Size,
    private val color: Color,
    private val starState: StarState
) {
    private val MaxRadius = 4f;

    private var initialDrawMillis: Long = 0L
    private var unitOffset: Offset = randomUnitOffset()
    private var initialRadius: Long = randomRadiusFor(size)

    fun DrawScope.draw(currentMillis: Long) {
        if (initialDrawMillis == 0L) {
            initialDrawMillis = currentMillis
        }
        val middle = Offset(size.width / 2f, size.height / 2f)
        val tick = (currentMillis - initialDrawMillis) * starState.warp

        // this is all derived by "does it look right" with a vague idea I wanted it to "accelerate"
        val extra: Float = (initialRadius * initialRadius).toFloat() / middle.getDistanceSquared()
        val offsetMultiplier = 1f + extra + tick / 100;
        val step: Float = (initialRadius / 10f).coerceIn(0.5f, 10f)

        val finalRadius = initialRadius + step * tick / 1_000 * offsetMultiplier
        val finalOffset = unitOffset.times(finalRadius) + middle
        if (finalOffset.isIn(size)) {
            drawCircle(
                color,
                center = finalOffset,
                radius = ((size.minDimension / initialRadius / 4)).coerceIn(2f, MaxRadius)
            );
        } else {
            // this star is no more, pick some new polar coords
            // Star is mutable here entirely as an optimization because list editing was a perf hit
            reset(size)
        }
    }

    override fun toString(): String {
        return "Star(unitOffset=$unitOffset, initialRadius=$initialRadius, initialDrawMillis=$initialDrawMillis)"
    }

    private fun reset(size: Size) {
        initialDrawMillis = 0L
        initialRadius = randomRadiusFor(size)
        unitOffset = randomUnitOffset()
    }

    private fun randomUnitOffset() = Random.nextDouble(Math.PI * 2).toUnitOffset()

    private fun randomRadiusFor(size: Size) =
        Random.nextLong(size.maxDimension.toLong() / 2).coerceAtLeast(5L)

}

private fun Double.toUnitOffset(): Offset = Offset(cos(this).toFloat(), sin(this).toFloat())

private fun Offset.isIn(size: Size): Boolean {
    return x >= 0 && x <= size.width && y >= 0 && y <= size.height
}
