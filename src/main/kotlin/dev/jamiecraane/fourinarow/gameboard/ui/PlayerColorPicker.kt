package dev.jamiecraane.fourinarow.gameboard.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dev.jamiecraane.fourinarow.common.ui.theme.FourInARowTheme
import dev.jamiecraane.fourinarow.extensions.color
import dev.jamiecraane.fourinarow.gameboard.domain.Piece

@Composable
fun PlayerPiecePicker(
    pieces: List<Piece>,
    selectedPiece: Piece,
    onPieceSelected: (Piece) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        pieces.forEach { piece ->
            PieceView(
                modifier = Modifier.align(Alignment.CenterVertically),
                piece = piece,
                selected = piece == selectedPiece
            ) { newlySelectedPiece ->
                onPieceSelected(newlySelectedPiece)
            }
        }
    }
}

@Composable
private fun PieceView(
    modifier: Modifier = Modifier,
    piece: Piece,
    selected: Boolean,
    onPieceSelected: (Piece) -> Unit,
) {
    val size = if (selected) 54.dp else 48.dp
    Box(contentAlignment = Alignment.Center, modifier = modifier.size(size = size)) {
        if (selected) {
            Box(
                modifier = Modifier.size(size)
                    .background(color = Color.Gray, shape = RoundedCornerShape(size / 2))
            ) {

            }
        }

        Box(
            modifier = Modifier.size(48.dp)
                .background(color = piece.color, shape = RoundedCornerShape(24.dp))
                .clickable(
                    remember { MutableInteractionSource() },
                    rememberRipple(bounded = true),
                    onClick = { onPieceSelected(piece) },
                )
        ) {

        }
    }
}

@Composable
@Preview
private fun PlayerColorPickerPreview() {
    FourInARowTheme {
        PlayerPiecePicker(pieces = Piece.values().toList(), selectedPiece = Piece.YELLOW, {})
    }
}

@Composable
@Preview
private fun PieceViewPreview() {
    FourInARowTheme {
        PieceView(piece = Piece.RED, selected = true, onPieceSelected = {})
    }
}
