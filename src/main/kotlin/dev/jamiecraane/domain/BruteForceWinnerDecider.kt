package dev.jamiecraane.domain

/**
 * Winner decider which checks the whole board horizontally, vertically or diagonally (there are two diagonals)
 * to check for four consecutive pieces. Short circuits if a winner is found.
 */
class BruteForceWinnerDecider : WinnerDecider {
    override fun hasWinner(board: Array<IntArray>): Piece? {
        val regex = createMatchWinnerRegex()

        val winnerHorizontal = checkWinnerHorizontal(board, regex)
        if (winnerHorizontal != null) {
            return winnerHorizontal
        }

        val winnerVertical = checkWinnerVertical(board, regex)
        if (winnerVertical != null) {
            return winnerVertical
        }

        return checkDiagonals(board, regex)
    }

    private fun createMatchWinnerRegex(): Regex {
        val valuesToMatch = Piece.values().map { it.code }
        val regex = buildString {
            append("(")
            valuesToMatch.forEach { code ->
                append("($code){$NUMBER_OF_CONSECUTIVE_REQUIRED}")
                append("|")
            }
            removeRange(this.length - 1, this.length)
            append(")")
        }.toRegex()
        return regex
    }

    private fun checkWinnerHorizontal(board: Array<IntArray>, regex: Regex): Piece? {
        for (col in board.indices) {
            val horizontal = StringBuilder()
            for (row in board[col].indices) {
                horizontal.append(board[col][row])
            }

            val winner = checkWinner(horizontal.toString(), regex)
            if (winner != null) {
                return winner
            }
        }

        return null
    }

    private fun checkWinnerVertical(board: Array<IntArray>, regex: Regex): Piece? {
        for (row in board[0].indices) {
            val vertical = StringBuilder()
            for (col in board.indices) {
                vertical.append(board[col][row])
            }

            val winner = checkWinner(vertical.toString(), regex)
            if (winner != null) {
                return winner
            }
        }

        return null
    }

    private fun checkDiagonals(board: Array<IntArray>, regex: Regex): Piece? {
        val winnerDiagonalTopLeft = checkDiagonal(board, regex)
        if (winnerDiagonalTopLeft != null) {
            return winnerDiagonalTopLeft
        }

        val boardReversed = reverseRowsInBoard(board)
        return checkDiagonal(boardReversed, regex)
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

    private fun checkDiagonal(board: Array<IntArray>, regex: Regex): Piece? {
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
            .firstNotNullOfOrNull { checkWinner(it, regex) }
    }

    private fun checkWinner(value: String, regex: Regex): Piece? {
        return regex.find(value)?.let { matchResult ->
            Piece.values().firstOrNull { piece ->
                matchResult.value.contains(piece.code.toString())
            }
        }
    }

    companion object {
        private const val NUMBER_OF_CONSECUTIVE_REQUIRED = 4
    }
}
