package dev.jamiecraane

import dev.jamiecraane.domain.GameBoard
import dev.jamiecraane.domain.Piece

fun main(args: Array<String>) {
    val board = arrayOf(
        intArrayOf(0, 0, 0, 0),
        intArrayOf(1, 2, 0, 0),
        intArrayOf(1, 1, 0, 2),
    )

    val numcol = board[0].size
    val numrow = board.size

//    Is this really more efficient than the other way? (perhaps only if we can iterate the array once)
    // Create one string of all values in the board
    val conc = board.fold(StringBuilder()) {builder, ints ->
        builder.append(ints.joinToString(""))
    }.toString()

    // concatenated: 000012001102
//                   0 1 2 3  4 5 6 7  8 9 10 11
    // for vertical: 0 0 0 0  1 2 0 0  1 1 0  2 (4*3)
    val rh = mutableListOf<String>()
    var h = ""
    for (i in conc.indices) {
        h += conc[i]
        if (h.length == 4) {
            rh += h
            h = ""
        }

        val index= i*numrow+4
        println(index)
//        rv2.get(i*numrow+4)
    }

    println(rh)

    val rv = mutableListOf<String>()
    var v = ""
    for (i in 0 until numcol) {
        for (j in 0 until numrow) {
            val index = (i)+(j*numcol)
            v += conc[index]
        }

        rv += v
        v = ""
    }

    println(rv)
}
