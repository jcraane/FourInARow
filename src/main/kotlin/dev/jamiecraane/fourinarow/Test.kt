package dev.jamiecraane.fourinarow

fun main(args: Array<String>) {
    val numRows = 7
    val numCols = 6

    val numPieces = numCols * numRows

    for (i in 0 until numPieces) {
        val r = i / numRows
        val c = (i%numRows)
        println("$i -> ($r, $c)")
    }
}
