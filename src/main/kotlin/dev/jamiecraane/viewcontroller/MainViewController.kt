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

    // List of pieces that are played until now.
    private val _playedPieces = MutableStateFlow<List<PieceViewModel>>(emptyList())
    val playedPieces: Flow<List<PieceViewModel>> = gameBoard.gameStatusFlow.map { gameStatus ->
        gameStatus?.playedPiece?.let {
            _playedPieces.value = _playedPieces.value + it.mapToViewModel()
        }

        _playedPieces.value
    }

    private val _timerState = MutableStateFlow(TimerViewModel("0s"))
    val timerState: Flow<TimerViewModel> = _timerState
    private var started = false
    private var elapsedSeconds = 0

    // Which piece to put in the game for the next move (switches between pieces because on every turn the other piece is played).
    private var whoIsNext: Piece = Piece.RED

    open fun init(viewModelScope: CoroutineScope) {
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
        _playedPieces.value = emptyList()
        whoIsNext = whoStarts
        _timerState.value = TimerViewModel()
        started = true
        startTimer()
    }

    @OptIn(ExperimentalTime::class)
    private fun startTimer() {
        viewModelScope.launch {
            while (started && isActive) {
                delay(1.seconds)
                elapsedSeconds++
                _timerState.value = TimerViewModel("${elapsedSeconds}s")
            }
        }


    }
    fun playPiece(column: Int) {
        gameBoard.put(whoIsNext, column)
        whoIsNext = whoIsNext.next()
    }

    fun onSettingsClicked() {
//        todo open settings screen
    }
}
