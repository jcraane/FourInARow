package dev.jamiecraane.fourinarow.gameboard.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Implements the game logic of 4 in a row.
 *
 * @param winnerDecider Class which implements the logic to decide who is the winner. Implemented as separate class so it is
 * easy testable and can be replaced with other, more efficient implementations later on.
 */
class FourInARow(private val winnerDecider: WinnerDecider = BruteForceWinnerDecider()) {
    private var board: Array<IntArray> = Array(NUM_ROWS) { IntArray(NUM_COLS) }
    private val _gameStatusFlow = MutableStateFlow<GameStatus?>(null)

    /**
     * Emits the game status on every piece played. @see [GameStatus]
     */
    val gameStatusFlow: Flow<GameStatus?> = _gameStatusFlow

    /**
     * Puts a new piece to the four in a row board.
     *
     * @param piece The piece to play.
     * @param column 0-based. First column is 0.
     */
    fun put(piece: Piece, column: Int) {
        var rowIndex = BOTTOM_ROW_INDEX // Start at bottom

//        If the column is full, don't do anything
        if (board[0][column] != EMPTY) {
            return
        }

        while (board[rowIndex][column] != EMPTY) {
            rowIndex--
        }

        if (board[rowIndex][column] == EMPTY) {
            board[rowIndex][column] = piece.code
        }

        _gameStatusFlow.value = GameStatus(
            winner = winnerDecider.hasWinner(board),
            playedPiece = PlayedPiece(piece, column, rowIndex),
        )
    }

    fun newGame() {
        _gameStatusFlow.value = null
        board = Array(NUM_ROWS) { IntArray(NUM_COLS) }
    }

    override fun toString(): String {
        return buildString {
            for (i in board.indices) {
                for (j in board[i].indices) {
                    append(board[i][j])
                    append(" ")
                }
                appendLine()
            }
        }
    }

    companion object {
        const val NUM_ROWS = 6
        const val NUM_COLS = 7
        private const val BOTTOM_ROW_INDEX = NUM_ROWS - 1

        const val EMPTY = 0
    }
}

/**
 * Represents the pieces that can be played.
 */
enum class Piece(val code: Int) {
    YELLOW(1) {
        override fun next() = RED
    },
    RED(2) {
        override fun next() = YELLOW
    };

    abstract fun next(): Piece

    companion object {
        fun fromCode(code: Int) = values().first { it.code == code }
    }
}

/**
 * Holds the current game status.
 *
 * @property winner If there is a winner, this indicates which piece has won.
 * @property playedPiece The last played piece. @see [PlayedPiece]. The playedPiece is set on the GameStatus because we only
 * know the row the piece has settled after the piece is played.
 */
data class GameStatus(val winner: Piece?, val playedPiece: PlayedPiece)

/**
 * Contains information about the played piece.
 *
 * @property piece Which piece was played
 * @property column At which column the piece was played.
 * @property row At which row the piece has settled.
 */
data class PlayedPiece(val piece: Piece, val column: Int, val row: Int)
