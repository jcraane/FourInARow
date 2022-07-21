package dev.jamiecraane.fourinarow.gameboard.viewcontroller

import dev.jamiecraane.fourinarow.gameboard.domain.Piece

data class MainScreenViewModel(
    val playedPieces: List<PieceViewModel> = emptyList(),
    val showSettings: Boolean = false,
    val winner: WinnerViewModel? = null,
    val whoIsNext: WhoIsNext? = null,
    val showNewGame: Boolean = false,
)

data class WhoIsNext(
    val piece: Piece, // We re-use PieceViewModel even though we do not need column and row.
    val name: String,
)
