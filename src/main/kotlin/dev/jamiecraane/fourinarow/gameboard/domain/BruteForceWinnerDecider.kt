package dev.jamiecraane.fourinarow.gameboard.domain

/**
 * Winner decider which checks the whole board horizontally, vertically or diagonally (there are two diagonals)
 * to check for four consecutive pieces. Short circuits if a winner is found.
 */
class BruteForceWinnerDecider : WinnerDecider {
    override fun hasWinner(board: Array<IntArray>): Piece? {
        val matchStrings = createWinnerMatchStrings()

        val winnerHorizontal = checkWinnerHorizontal(board, matchStrings)
        if (winnerHorizontal != null) {
            return winnerHorizontal
        }

        val winnerVertical = checkWinnerVertical(board, matchStrings)
        if (winnerVertical != null) {
            return winnerVertical
        }

        return checkDiagonals(board, matchStrings)
    }

    private fun createWinnerMatchStrings(): List<String> =
        Piece.values().map { piece ->
            buildString {
                repeat(NUMBER_OF_CONSECUTIVE_REQUIRED) {
                    append(piece.code.toString())
                }
            }
        }

    private fun checkWinnerHorizontal(board: Array<IntArray>, matchStrings: List<String>): Piece? {
        for (col in board.indices) {
            val horizontal = StringBuilder()
            for (row in board[col].indices) {
                horizontal.append(board[col][row])
            }

            val winner = checkWinner(horizontal.toString(), matchStrings)
            if (winner != null) {
                return winner
            }
        }

        return null
    }

    private fun checkWinnerVertical(board: Array<IntArray>, matchStrings: List<String>): Piece? {
        for (row in board[0].indices) {
            val vertical = StringBuilder()
            for (col in board.indices) {
                vertical.append(board[col][row])
            }

            val winner = checkWinner(vertical.toString(), matchStrings)
            if (winner != null) {
                return winner
            }
        }

        return null
    }

    private fun checkDiagonals(board: Array<IntArray>, matchStrings: List<String>): Piece? {
        val winnerDiagonalTopLeft = checkDiagonal(board, matchStrings)
        if (winnerDiagonalTopLeft != null) {
            return winnerDiagonalTopLeft
        }

        val boardReversed = reverseRowsInBoard(board)
        return checkDiagonal(boardReversed, matchStrings)
    }

    private fun reverseRowsInBoard(board: Array<IntArray>): Array<IntArray> {
        val numCols = board[0].size
        val numRows = board.size
        val boardWithReversedColumns = Array(numRows) {
            intArrayOf(numCols)
        }
        for (row in board.indices) {
            boardWithReversedColumns[row] = board[row].reversedArray()
        }

        return boardWithReversedColumns
    }

    private fun checkDiagonal(board: Array<IntArray>, matchStrings: List<String>): Piece? {
        val numCols = board[0].size
        val numRows = board.size
        val numDiagonals = numCols + numRows - 1

        val diagonals: MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until numDiagonals) {
            diagonals.add(mutableListOf())
        }

        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                diagonals[row + col].add(board[row][col])
            }
        }

        return diagonals
            .filter { it.size >= NUMBER_OF_CONSECUTIVE_REQUIRED }
            .map { it.joinToString("") }
            .firstNotNullOfOrNull { checkWinner(it, matchStrings) }
    }

    private fun checkWinner(value: String, matchStrings: List<String>): Piece? {
        return matchStrings.find { value.contains(it) }
            ?.let { found ->
                Piece.fromCode(found.first().toString().toInt())
            }
    }

    companion object {
        private const val NUMBER_OF_CONSECUTIVE_REQUIRED = 4
    }
}
