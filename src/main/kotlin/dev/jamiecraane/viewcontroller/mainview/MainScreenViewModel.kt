package dev.jamiecraane.viewcontroller.mainview

data class MainScreenViewModel(
    val playedPieces: List<PieceViewModel> = emptyList(),
    val showSettings: Boolean = false,
    val winner: WinnerViewModel? = null,
//    todo who is next to show it in the UI
)
