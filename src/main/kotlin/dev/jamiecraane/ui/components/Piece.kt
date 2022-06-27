package dev.jamiecraane.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.jamiecraane.ui.theme.FourInARowTheme

@Composable
fun Piece(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(modifier = modifier
        .size(66.dp)
        .background(color, RoundedCornerShape(33.dp))) {

    }
}

@Composable
@Preview
private fun PiecePreview() {
    FourInARowTheme {
        Piece(color = Color.Red)
    }
}
