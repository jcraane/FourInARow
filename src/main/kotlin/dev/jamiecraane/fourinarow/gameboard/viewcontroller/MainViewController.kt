package dev.jamiecraane.fourinarow.gameboard.viewcontroller

import dev.jamiecraane.fourinarow.gameboard.domain.FourInARow
import dev.jamiecraane.fourinarow.gameboard.domain.Piece
import dev.jamiecraane.fourinarow.gameboard.domain.PlayedPiece
import dev.jamiecraane.fourinarow.extensions.color
import dev.jamiecraane.fourinarow.settings.repository.SettingsRepository
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
    private val showNewGameDialog = MutableStateFlow(false)

    // Which piece to put in the game for the next move (switches between pieces because on every turn the other piece is played).
    private val whoIsNext = MutableStateFlow(Piece.RED)

    val mainScreenState: Flow<MainScreenViewModel> = combine(
        gameBoard.gameStatusFlow, showSettings, whoIsNext, showNewGameDialog
    ) { gameStatus, showSettings, whoIsNext, showNewGame ->
        gameStatus?.playedPiece?.mapToViewModel()?.let { playedPiece ->
            playedPieces.add(playedPiece)
        }

        MainScreenViewModel(
            playedPieces.toList(),
            showSettings,
            winner = if (gameStatus?.winner != null) WinnerViewModel(name = gameStatus.winner.name) else null,
            whoIsNext = WhoIsNext(
                piece = PieceViewModel(whoIsNext.color, 0, 0),
                name = "",
            ),
            showNewGame = showNewGame,
        )
    }

    private val _timerState = MutableStateFlow(TimerViewModel("0s"))
    val timerState: Flow<TimerViewModel> = _timerState

    private var started = false
    private var elapsedSeconds = 0
    private var timerJob: Job? = null

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    private fun PlayedPiece.mapToViewModel(): PieceViewModel = PieceViewModel(color = this.piece.color, column, row)

    /**
     * Starts a new game.
     *
     * @param whoStarts The color of the piece who starts the game.
     */
    fun startNewGame(whoStarts: Piece = Piece.RED) {
        showNewGameDialog.value = false
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

    fun closeSettingsDialog() {
        showSettings.value = false
    }

    fun onNewGameClicked() {
        showNewGameDialog.value = true
    }

    fun closeNewGameDialog() {
        showNewGameDialog.value = false
    }
}
