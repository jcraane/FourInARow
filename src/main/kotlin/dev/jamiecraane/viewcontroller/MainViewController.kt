package dev.jamiecraane.viewcontroller

import androidx.compose.ui.graphics.Color
import dev.jamiecraane.domain.FourInARow
import dev.jamiecraane.domain.Piece
import dev.jamiecraane.domain.PlayedPiece
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

/**
 * View controller who handles interaction between the four in a row views and the game logic itself.
 *
 * Delegates to @see [FourInARow] which implements the actual game logic.
 */
open class MainViewController {
    private lateinit var viewModelScope: CoroutineScope

    var gameBoard = FourInARow()
        private set

    private val playedPieces = mutableSetOf<PieceViewModel>()
    private val showSettings = MutableStateFlow(false)
    private val showWinner = MutableStateFlow(false)
    private val timerState = MutableStateFlow(TimerViewModel("0s"))

    val mainScreenState: Flow<MainScreenViewModel> = combine(
        gameBoard.gameStatusFlow, timerState, showSettings, showWinner
    ) { gameStatus, timerState, showSettings, showWinner ->
        gameStatus?.playedPiece?.mapToViewModel()?.let { playedPiece ->
            playedPieces.add(playedPiece)
        }

        MainScreenViewModel(
            playedPieces.toList(),
            timerState,
            showSettings,
            showWinner
        )
    }

    private var started = false
    private var elapsedSeconds = 0

    // Which piece to put in the game for the next move (switches between pieces because on every turn the other piece is played).
    private var whoIsNext: Piece = Piece.RED

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    private fun PlayedPiece.mapToViewModel(): PieceViewModel = when (this.piece) {
        Piece.YELLOW -> PieceViewModel(color = Color.Red, column, row)
        Piece.RED -> PieceViewModel(color = Color.Yellow, column, row)
    }

    /**
     * Starts a new game.
     *
     * @param whoStarts The color of the piece who starts the game.
     */
    fun newGame(whoStarts: Piece = Piece.RED) {
        this.gameBoard = FourInARow()
        whoIsNext = whoStarts
        started = true
        startTimer()
    }

    @OptIn(ExperimentalTime::class)
    private fun startTimer() {
        elapsedSeconds = 0
        timerState.value = TimerViewModel()

        viewModelScope.launch {
            while (started && isActive) {
                delay(1.seconds)
                elapsedSeconds++
                timerState.value = TimerViewModel("${elapsedSeconds}s")
            }
        }
    }

    fun playPiece(column: Int) {
        gameBoard.put(whoIsNext, column)
        whoIsNext = whoIsNext.next()
    }

    fun onSettingsClicked() {
        showSettings.value = true
    }

    fun closeWinnerDialog() {
        showSettings.value = false
    }
}
