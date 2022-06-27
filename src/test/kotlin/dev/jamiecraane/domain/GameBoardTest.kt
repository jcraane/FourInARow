package dev.jamiecraane.domain

import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GameBoardTest {
    @Test
    fun putPiece() = runTest() {
        val board = FourInARow()

        launch {
            board.gameStatusFlow.test {
                Assertions.assertEquals(GameStatus(null, PlayedPiece(Piece.RED, 0, 5)), awaitItem())
                Assertions.assertEquals(GameStatus(null, PlayedPiece(Piece.YELLOW, 0, 4)), awaitItem())
                Assertions.assertEquals(GameStatus(null, PlayedPiece(Piece.RED, 0, 3)), awaitItem())
                Assertions.assertEquals(GameStatus(null, PlayedPiece(Piece.YELLOW, 3, 5)), awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }

//        Use slight delay or else we might skip emissions because the unit test (collector) seems slower than the emitter (Gameboard):
//        See: https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/
        board.put(Piece.RED, 0)
        delay(100)
        board.put(Piece.YELLOW, 0)
        delay(100)
        board.put(Piece.RED, 0)
        delay(100)
        board.put(Piece.YELLOW, 3)
    }
}
