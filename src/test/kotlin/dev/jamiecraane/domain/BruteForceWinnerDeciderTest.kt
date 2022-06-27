package dev.jamiecraane.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BruteForceWinnerDeciderTest {
    private val winnerDecider = BruteForceWinnerDecider()

    @Test
    fun checkWinner() {
        // Horizontal
        assertEquals(
            Piece.YELLOW, winnerDecider.hasWinner(
                arrayOf(
                    intArrayOf(0, 0, 0, 0, 0, 0, 0),
                    intArrayOf(1, 1, 1, 1, 0, 0, 0)
                )
            )
        )

        // Vertical
        assertEquals(
            Piece.YELLOW, winnerDecider.hasWinner(
                arrayOf(
                    intArrayOf(0, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 1, 0, 0, 0, 0, 0),
                )
            )
        )

        // Diagonal
        assertEquals(
            Piece.YELLOW, winnerDecider.hasWinner(
                arrayOf(
                    intArrayOf(1, 0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 1, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 1, 0, 0, 0),
                )
            )
        )

        assertEquals(
            Piece.YELLOW, winnerDecider.hasWinner(
                arrayOf(
                    intArrayOf(0, 0, 0, 0, 1, 0, 0),
                    intArrayOf(0, 0, 0, 1, 0, 0, 0),
                    intArrayOf(0, 0, 1, 0, 0, 0, 0),
                    intArrayOf(0, 1, 0, 0, 0, 0, 0),
                )
            )
        )

        assertEquals(
            null, winnerDecider.hasWinner(
                arrayOf(
                    intArrayOf(1, 0, 0, 0, 2, 0, 0),
                    intArrayOf(0, 1, 0, 2, 0, 1, 0),
                    intArrayOf(0, 0, 2, 0, 0, 1, 0),
                    intArrayOf(0, 0, 0, 1, 0, 2, 0),
                )
            )
        )
    }
}
