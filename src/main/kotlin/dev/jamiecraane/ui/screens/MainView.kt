package dev.jamiecraane.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.domain.FourInARow
import dev.jamiecraane.domain.Piece
import dev.jamiecraane.ui.components.Board
import dev.jamiecraane.ui.components.GameActionsStrip
import dev.jamiecraane.ui.theme.FourInARowTheme
import dev.jamiecraane.util.MainViewController

@Composable
fun MainView(mainViewController: MainViewController) {
    val gameState by mainViewController.gameBoard.gameStatusFlow.collectAsState(null)

    MainViewContent(
        winner = gameState?.winner,
        onNewGameClicked = { mainViewController.newGame() },
        onSettingsClicked = {}
    )
}

@Composable
fun MainViewContent(
    winner: Piece?,
    onNewGameClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Row() {
        GameActionsStrip(
            onNewGameClicked = onNewGameClicked,
            onSettingsClicked = onSettingsClicked,
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Row() {
//                todo should contains time. Winner should be showed in popup panel.
                /*Text(text = "Winner: ")
                winner?.let {
                    Text(text = it.name)
                }*/
            }
            Spacer(modifier = Modifier.height(12.dp))
            Board(
                numColumns = FourInARow.NUM_COLS,
                numRows = FourInARow.NUM_ROWS,
                onClickListener = { column ->
                    println("Add new piece at column $column")
                },
                playedPieces = emptyList(),
            )
        }
    }
}

@Composable
@Preview
private fun MainViewContentPreview() {
    FourInARowTheme {
        MainViewContent(winner = null, onNewGameClicked = {}, onSettingsClicked = {})
    }
}
