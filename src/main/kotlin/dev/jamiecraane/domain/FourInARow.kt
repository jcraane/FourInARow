package dev.jamiecraane.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FourInARow(private val winnerDecider: WinnerDecider = BruteForceWinnerDecider()) {
    private val board: Array<IntArray> = Array(NUM_ROWS) { IntArray(NUM_COLS) }
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

enum class Piece(val code: Int) {
    YELLOW(1), RED(2);
}

/**
 * Holds the current game status.
 *
 * @property winner If there is a winner, this indicates which piece has won.
 * @property playedPiece The last played piece. @see [PlayedPiece]
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
