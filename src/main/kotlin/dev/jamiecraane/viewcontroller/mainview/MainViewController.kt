package dev.jamiecraane.viewcontroller.mainview

import androidx.compose.ui.graphics.Color
import dev.jamiecraane.domain.FourInARow
import dev.jamiecraane.domain.Piece
import dev.jamiecraane.domain.PlayedPiece
import dev.jamiecraane.persistence.SettingsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

/**
 * View controller who handles interaction between the four in a row views and the game logic itself.
 *
 * Delegates to @see [FourInARow] which implements the actual game logic.
 */
open class MainViewController(
    private val settingsRepository: SettingsRepository,
) {
    private lateinit var viewModelScope: CoroutineScope

    private val gameBoard = FourInARow()

    private val playedPieces = mutableSetOf<PieceViewModel>()
    private val showSettings = MutableStateFlow(false)
    // Which piece to put in the game for the next move (switches between pieces because on every turn the other piece is played).
    private val whoIsNext = MutableStateFlow(Piece.RED)

    val mainScreenState: Flow<MainScreenViewModel> = combine(
        gameBoard.gameStatusFlow, showSettings, whoIsNext
    ) { gameStatus, showSettings, whoIsNext ->
        gameStatus?.playedPiece?.mapToViewModel()?.let { playedPiece ->
            playedPieces.add(playedPiece)
        }

        MainScreenViewModel(
            playedPieces.toList(),
            showSettings,
            winner = if (gameStatus?.winner != null) WinnerViewModel(name = gameStatus.winner.name) else null,
        )
    }

    private val _timerState = MutableStateFlow(TimerViewModel("0s"))
    val timerState:Flow<TimerViewModel> = _timerState

    private var started = false
    private var elapsedSeconds = 0
    private var timerJob: Job? = null

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    private fun PlayedPiece.mapToViewModel(): PieceViewModel = when (this.piece) {
        Piece.YELLOW -> PieceViewModel(color = Color(0xFFF8FF62), column, row)
        Piece.RED -> PieceViewModel(color = Color(0xFFFF7E6C), column, row)
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
        whoIsNext.value = whoStarts
        startTimer()
    }

    fun reset(whoStarts: Piece = Piece.RED) {
        resetTimer()
        this.gameBoard.newGame()
        playedPieces.clear()
        this.whoIsNext.value = whoStarts
    }

    @OptIn(ExperimentalTime::class)
    private fun startTimer() {
        resetTimer()
        started = true

        timerJob = viewModelScope.launch {
            while (started && isActive) {
                delay(1.seconds)
                elapsedSeconds++
                _timerState.value = TimerViewModel("${elapsedSeconds}s")
            }
        }
    }

    private fun resetTimer() {
        timerJob?.cancel()
        started = false
        elapsedSeconds = 0
        _timerState.value = TimerViewModel()
    }

    fun playPiece(column: Int) {
        if (!started) {
            startTimer()
        }
        gameBoard.put(whoIsNext.value, column)
        whoIsNext.value = whoIsNext.value.next()
    }

    fun onSettingsClicked() {
        showSettings.value = true
    }

    fun closeWinnerDialog() {
        showSettings.value = false
    }
}
