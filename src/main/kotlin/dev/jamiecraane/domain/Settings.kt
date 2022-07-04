package dev.jamiecraane.domain

//todo how do we determine who starts the game? A player has a name associated with a color? ie. Jan=Yellow, John=Red. The loser of the
// last game wins. First game user needs to choose who starts first.
data class Settings(
    val playerOne: String = "Player 1",
    val playerTwo: String = "Player 2",
)
