package dev.jamiecraane.domain

interface WinnerDecider {
    /**
     * Checks if there is a winner on the current board and returns the piece who won. Null otherwise.
     *
     * @return The piece that has one or null if there is no winner yet.
     */
    fun hasWinner(board: Array<IntArray>): Piece?
}
