package dev.jamiecraane.ui.components.core

import androidx.compose.animation.animateColorAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.jamiecraane.ui.theme.FourInARowTheme
import dev.jamiecraane.ui.theme.gameButtonBackground

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    label: String,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,) {

    val colors = rememberButtonColors(
        interactionSource = interactionSource,
        backgroundColorPressed = gameButtonBackground,
        backgroundColorNormal = gameButtonBackground,
        contentColorPressed = MaterialTheme.colors.onPrimary,
        contentColorNormal = MaterialTheme.colors.onPrimary
    )

    Button(
        modifier = modifier,
        colors = colors,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.background(gameButtonBackground),
            text = label)
    }
}

@Composable
fun rememberButtonColors(
    interactionSource: InteractionSource,
    backgroundColorPressed: Color,
    backgroundColorNormal: Color,
    contentColorPressed: Color,
    contentColorNormal: Color,
) = remember(
    interactionSource,
    backgroundColorPressed,
    backgroundColorNormal,
    contentColorPressed,
    contentColorNormal
) {
    object : ButtonColors {
        private val isPressed
            @Composable get() = interactionSource.collectIsPressedAsState().value

        @Composable
        override fun backgroundColor(enabled: Boolean): State<Color> = animateColorAsState(
            if (isPressed) backgroundColorPressed
            else backgroundColorNormal
        )

        @Composable
        override fun contentColor(enabled: Boolean): State<Color> = animateColorAsState(
            if (isPressed) contentColorPressed
            else contentColorNormal
        )
    }
}

@Composable
@Preview
private fun GameButtonPreview() {
    FourInARowTheme {
        ActionButton(onClick = {}, label = "New Game")
    }
}
