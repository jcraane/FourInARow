package dev.jamiecraane.fourinarow.gameboard.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.fourinarow.common.ui.components.core.ActionButton
import dev.jamiecraane.fourinarow.common.ui.theme.FourInARowTheme
import dev.jamiecraane.fourinarow.gameboard.domain.Piece
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.MainViewController
import dev.jamiecraane.fourinarow.settings.domain.Settings
import dev.jamiecraane.fourinarow.settings.viewcontroller.SettingsViewController

@Composable
fun NewGameDialogView(
    mainViewController: MainViewController,
    settingsViewController: SettingsViewController,
) {
    NewGameDialogContent(
        onCloseClicked = { mainViewController.closeNewGameDialog() },
        onStartNewGameClicked = { mainViewController.startNewGame() },
        settings = settingsViewController.retrieveSettings(),
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NewGameDialogContent(
    settings: Settings,
    onStartNewGameClicked: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    var piecePlayerOne by remember { mutableStateOf(Piece.YELLOW) }
    var piecePlayerTwo by remember { mutableStateOf(Piece.RED) }

    AlertDialog(
        modifier = Modifier.padding(12.dp).width(350.dp),
        onDismissRequest = { onCloseClicked() },
        title = {
            Column {
                Text(text = "New game")
                Spacer(modifier = Modifier.height(12.dp))
                Row() {
                    Text(text = settings.playerOne, modifier = Modifier.weight(1f))
                    PlayerPiecePicker(
                        pieces = Piece.values().toList(),
                        selectedPiece = piecePlayerOne,
                        onPieceSelected = {
                            piecePlayerOne = it
                            piecePlayerTwo = it.next()
                        }
                    )
                }
                Row() {
                    Text(text = settings.playerTwo, modifier = Modifier.weight(1f))
                    PlayerPiecePicker(
                        pieces = Piece.values().toList(),
                        selectedPiece = piecePlayerTwo,
                        onPieceSelected = {
                            piecePlayerTwo = it
                            piecePlayerOne = it.next()
                        }
                    )

                }
            }
        },
        buttons = {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(label = "Start", onClick = {
                    onStartNewGameClicked()
                })
                ActionButton(label = "Cancel", onClick = {
                    onCloseClicked()
                })
            }
        },
    )
}

@Composable
@Preview
private fun PlayerColorPickerPreview() {
    FourInARowTheme {
//        PlayerPiecePicker()
    }
}

@Composable
@Preview
private fun NewGameDialogContentPreview() {
    FourInARowTheme {
        NewGameDialogContent(onStartNewGameClicked = {}, onCloseClicked = {}, settings = Settings())
    }
}

