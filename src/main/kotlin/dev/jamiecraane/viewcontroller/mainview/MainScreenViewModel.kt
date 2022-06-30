package dev.jamiecraane.viewcontroller.mainview

data class MainScreenViewModel(
    val playedPieces: List<PieceViewModel> = emptyList(),
    val showSettings: Boolean = false,
    val winner: WinnerViewModel? = null,
)
