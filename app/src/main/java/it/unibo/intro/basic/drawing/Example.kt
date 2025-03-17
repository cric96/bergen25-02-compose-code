package it.unibo.intro.basic.drawing

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Animation values for the rolling ball
 */
@Composable
private fun rememberRollingBallAnimationState(speed: Float, width: Float): Pair<State<Float>, State<Float>> {
    val infiniteTransition = rememberInfiniteTransition(label = "ball-animation")

    // Calculate common animation duration
    val durationMillis = (1000f / speed * 1000f).toInt()
    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(
            durationMillis = durationMillis,
            easing = LinearEasing
        ),
        repeatMode = RepeatMode.Restart
    )

    // Create animated position
    val xPosition = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = width,
        animationSpec = animationSpec,
        label = "x-position"
    )

    // Create animated rotation
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = animationSpec,
        label = "rotation"
    )

    return Pair(xPosition, rotation)
}

/**
 * A canvas component that renders a rolling ball animation
 */
@Composable
fun RollingBall(
    modifier: Modifier = Modifier,
    ballColor: Color = MaterialTheme.colorScheme.primary,
    speed: Float = 200f, // pixels per second
) {
    var sizeState by remember { mutableFloatStateOf(1000f) }
    val (xPosition, rotation) = rememberRollingBallAnimationState(speed, sizeState)
    Canvas(modifier = modifier.fillMaxWidth().height(200.dp)) {
        sizeState = size.width
        val radius = 50f
        val canvasWidth = size.width
        val y = size.height / 2

        // Calculate position (ensure it stays within bounds)
        val x = (xPosition.value % (canvasWidth + 2 * radius)) - radius

        drawGroundLine(y, radius)
        drawRotatingBall(x, y, radius, rotation.value, ballColor)
    }
}

/**
 * Draws the ground line for the ball to roll on
 */
private fun DrawScope.drawGroundLine(y: Float, radius: Float) {
    drawLine(
        color = Color.Gray,
        start = Offset(0f, y + radius),
        end = Offset(size.width, y + radius),
        strokeWidth = 2.dp.toPx()
    )
}

/**
 * Draws the ball with rotation effect
 */
private fun DrawScope.drawRotatingBall(
    x: Float,
    y: Float,
    radius: Float,
    rotationDegrees: Float,
    ballColor: Color
) {
    rotate(degrees = rotationDegrees, pivot = Offset(x, y)) {
        // Main ball
        drawCircle(
            color = ballColor,
            radius = radius,
            center = Offset(x, y)
        )

        // Ball details (to visualize rotation)
        drawCircle(
            color = Color.Black,
            radius = radius,
            center = Offset(x, y),
            style = Stroke(width = 2.dp.toPx())
        )

    }
}

/**
 * An interactive demonstration of the rolling ball
 */
@Composable
fun RollingBallDemo() {
    val speed by remember { mutableFloatStateOf(1000f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rolling Ball Demo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        RollingBall(
            modifier = Modifier.weight(1f),
            speed = speed
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RollingBallPreview() {
    RollingBallDemo()
}