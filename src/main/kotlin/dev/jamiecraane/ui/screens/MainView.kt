package dev.jamiecraane.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.domain.FourInARow
import dev.jamiecraane.domain.Piece
import dev.jamiecraane.ui.components.ActionStrip
import dev.jamiecraane.ui.components.Board
import dev.jamiecraane.ui.theme.FourInARowTheme
import dev.jamiecraane.viewcontroller.MainViewController
import dev.jamiecraane.viewcontroller.SettingsViewController
import dev.jamiecraane.viewcontroller.TimerViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(
    mainViewController: MainViewController,
    settingsViewController: SettingsViewController,
) {
    val gameState by mainViewController.gameBoard.gameStatusFlow.collectAsState(null)
    val timerState by mainViewController.timerState.collectAsState(TimerViewModel())
    val showSettings by mainViewController.showSettings.collectAsState(false)

    MainViewContent(
        winner = gameState?.winner,
        timerState = timerState,
        onNewGameClicked = { mainViewController.newGame() },
        onSettingsClicked = { mainViewController.onSettingsClicked() },
        onPieceClicked = { column -> mainViewController.playPiece(column) }
    )

    if (showSettings) {
        SettingsDialogView(mainViewController, settingsViewController)
    }
}

@Composable
fun MainViewContent(
    winner: Piece?,
    timerState: TimerViewModel,
    onNewGameClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onPieceClicked: (column: Int) -> Unit,
) {
    Row() {
        ActionStrip(
            onNewGameClicked = onNewGameClicked,
            onSettingsClicked = onSettingsClicked,
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Row() {
                Text(text = "Elapsed: ${timerState.elapsed}")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Board(
                numColumns = FourInARow.NUM_COLS,
                numRows = FourInARow.NUM_ROWS,
                onClickListener = { column -> onPieceClicked(column) },
                playedPieces = emptyList(),
            )
        }
    }
}

@Composable
@Preview
private fun MainViewContentPreview() {
    FourInARowTheme {
        MainViewContent(winner = null, timerState = TimerViewModel(), onNewGameClicked = {}, onSettingsClicked = {}, onPieceClicked = {})
    }
}
