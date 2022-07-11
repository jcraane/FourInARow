package dev.jamiecraane.fourinarow.gameboard.viewcontroller

data class MainScreenViewModel(
    val playedPieces: List<PieceViewModel> = emptyList(),
    val showSettings: Boolean = false,
    val winner: WinnerViewModel? = null,
    val whoIsNext: WhoIsNext? = null,
    val showNewGame: Boolean = false,
)

data class WhoIsNext(
    val piece: PieceViewModel, // We re-use PieceViewModel even though we do not need column and row.
    val name: String,
)
