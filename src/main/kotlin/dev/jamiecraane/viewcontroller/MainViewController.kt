package dev.jamiecraane.viewcontroller

import androidx.compose.ui.graphics.Color
import dev.jamiecraane.domain.FourInARow
import dev.jamiecraane.domain.Piece
import dev.jamiecraane.domain.PlayedPiece
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

/**
 * View controller who handles interaction between the four in a row views and the game logic itself.
 *
 * Delegates to @see [FourInARow] which implements the actual game logic.
 */
open class MainViewController {
    private lateinit var viewModelScope: CoroutineScope

    private val gameBoard = FourInARow()

    private val playedPieces = mutableSetOf<PieceViewModel>()
    private val showSettings = MutableStateFlow(false)
    private val timerState = MutableStateFlow(TimerViewModel("0s"))

    val mainScreenState: Flow<MainScreenViewModel> = combine(
        gameBoard.gameStatusFlow, timerState, showSettings
    ) { gameStatus, timerState, showSettings ->
        gameStatus?.playedPiece?.mapToViewModel()?.let { playedPiece ->
            println("Add played piece = $playedPiece")
            playedPieces.add(playedPiece)
        }

        println("WINNER = ${gameStatus?.winner}")
        MainScreenViewModel(
            playedPieces.toList(),
            timerState,
            showSettings,
            gameStatus?.winner != null,
        )
    }

    private var started = false
    private var elapsedSeconds = 0
    private var timerJob: Job? = null

    // Which piece to put in the game for the next move (switches between pieces because on every turn the other piece is played).
    private var whoIsNext: Piece = Piece.RED

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    private fun PlayedPiece.mapToViewModel(): PieceViewModel = when (this.piece) {
        Piece.YELLOW -> PieceViewModel(color = Color.Yellow, column, row)
        Piece.RED -> PieceViewModel(color = Color.Red, column, row)
    }

    /**
     * Starts a new game.
     *
     * @param whoStarts The color of the piece who starts the game.
     */
    fun newGame(whoStarts: Piece = Piece.RED) {
        resetTimer()
        this.gameBoard.newGame()
        playedPieces.clear()
        whoIsNext = whoStarts
        startTimer()
    }

    fun reset(whoStarts: Piece = Piece.RED) {
        resetTimer()
        this.gameBoard.newGame()
        playedPieces.clear()
        whoIsNext = whoStarts
    }

    @OptIn(ExperimentalTime::class)
    private fun startTimer() {
        resetTimer()
        started = true

        timerJob = viewModelScope.launch {
            while (started && isActive) {
                delay(1.seconds)
                elapsedSeconds++
                timerState.value = TimerViewModel("${elapsedSeconds}s")
            }
        }
    }

    private fun resetTimer() {
        timerJob?.cancel()
        started = false
        elapsedSeconds = 0
        timerState.value = TimerViewModel()
    }

    fun playPiece(column: Int) {
        if (!started) {
            startTimer()
        }
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
