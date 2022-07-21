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
    val piecesForPlayer = mainViewController.piecesForPlayer()
    NewGameDialogContent(
        settings = settingsViewController.retrieveSettings(),
        initialPiecePlayerOne = piecesForPlayer.first,
        initialPiecePlayerTwo = piecesForPlayer.second,
        onCloseClicked = { mainViewController.closeNewGameDialog() },
        onStartNewGameClicked = { playerOne, playerTwo ->
            mainViewController.startNewGame(playerOne, playerTwo)
        },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NewGameDialogContent(
    settings: Settings,
    initialPiecePlayerOne: Piece,
    initialPiecePlayerTwo: Piece,
    onStartNewGameClicked: (playerOne: Piece, playerTwo: Piece) -> Unit,
    onCloseClicked: () -> Unit,
) {
    var piecePlayerOne by remember { mutableStateOf(initialPiecePlayerOne) }
    var piecePlayerTwo by remember { mutableStateOf(initialPiecePlayerTwo) }

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
                    onStartNewGameClicked(piecePlayerOne, piecePlayerTwo)
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
        PlayerPiecePicker(pieces = Piece.values().toList(), selectedPiece = Piece.YELLOW, {})
    }
}

@Composable
@Preview
private fun NewGameDialogContentPreview() {
    FourInARowTheme {
        NewGameDialogContent(onStartNewGameClicked = { playerOne, playerTwo -> }, onCloseClicked = {}, settings = Settings(),
            initialPiecePlayerTwo = Piece.YELLOW, initialPiecePlayerOne = Piece.RED
        )
    }
}

