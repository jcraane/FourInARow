package dev.jamiecraane.fourinarow.extensions

import androidx.compose.ui.graphics.Color
import dev.jamiecraane.fourinarow.gameboard.domain.Piece

val Piece.color: Color
    get() {
        return when (this) {
            Piece.YELLOW -> Color(0xFFF8FF62)
            Piece.RED -> Color(0xFFFF7E6C)
        }
    }
