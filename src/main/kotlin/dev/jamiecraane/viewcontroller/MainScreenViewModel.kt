package dev.jamiecraane.viewcontroller

data class MainScreenViewModel(
    val playedPieces: List<PieceViewModel> = emptyList(),
    val timerViewModel: TimerViewModel = TimerViewModel(),
    val showSettings: Boolean = false,
    val winner: WinnerViewModel? = null,
)
