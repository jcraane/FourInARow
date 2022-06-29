package dev.jamiecraane.util

import dev.jamiecraane.domain.FourInARow
import dev.jamiecraane.domain.Piece
import dev.jamiecraane.domain.PieceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

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
    private val _playedPieces=  MutableStateFlow<List<PieceViewModel>>(emptyList())
    val playedPieces: Flow<List<PieceViewModel>> = _playedPieces

    // Which piece to put in the game for the next move (switches between pieces because on every turn the other piece is played).
    private var whoIsNext: Piece = Piece.RED

    open fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
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
    }

    fun playPiece(column: Int) {
        gameBoard.put(whoIsNext, column)
        whoIsNext = whoIsNext.next()
    }
}
