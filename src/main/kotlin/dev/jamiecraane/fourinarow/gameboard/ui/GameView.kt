package dev.jamiecraane.fourinarow.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.fourinarow.gameboard.domain.FourInARow
import dev.jamiecraane.fourinarow.common.ui.components.ActionStrip
import dev.jamiecraane.fourinarow.common.ui.components.Board
import dev.jamiecraane.fourinarow.common.ui.theme.FourInARowTheme
import dev.jamiecraane.fourinarow.gameboard.ui.NewGameDialogView
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.MainScreenViewModel
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.MainViewController
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.PieceViewModel
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.TimerViewModel
import dev.jamiecraane.fourinarow.main
import dev.jamiecraane.fourinarow.settings.viewcontroller.SettingsViewController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameView(
    mainViewController: MainViewController,
    settingsViewController: SettingsViewController,
) {
    val mainScreenState by mainViewController.mainScreenState.collectAsState(MainScreenViewModel())
    val timerState by mainViewController.timerState.collectAsState(TimerViewModel())

    MainViewContent(
        timerState = timerState,
        onNewGameClicked = { mainViewController.onNewGameClicked() },
        onSettingsClicked = { mainViewController.onSettingsClicked() },
        onPieceClicked = { column -> mainViewController.playPiece(column) },
        playedPieces = mainScreenState.playedPieces,
    )

    if (mainScreenState.showSettings) {
        SettingsDialogView(mainViewController, settingsViewController)
    }

    mainScreenState.winner?.let {
        WinnerDialogView(it, mainViewController)
    }

    if (mainScreenState.showNewGame) {
        NewGameDialogView(mainViewController, settingsViewController)
    }
}

@Composable
fun MainViewContent(
    timerState: TimerViewModel,
    playedPieces: List<PieceViewModel>,
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
                playedPieces = playedPieces,
            )
        }
    }
}

@Composable
@Preview
private fun MainViewContentPreview() {
    FourInARowTheme {
        MainViewContent(
            timerState = TimerViewModel(),
            playedPieces = emptyList(),
            onNewGameClicked = {},
            onSettingsClicked = {},
            onPieceClicked = {},
        )
    }
}
