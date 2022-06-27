package dev.jamiecraane

import dev.jamiecraane.domain.GameBoard
import dev.jamiecraane.domain.Piece

fun main(args: Array<String>) {
    val board = GameBoard()
    println(board)

    board.put(Piece.YELLOW, 3)
    board.put(Piece.RED, 3)
    board.put(Piece.YELLOW, 4)
    board.put(Piece.RED, 2)
    println(board)
}
