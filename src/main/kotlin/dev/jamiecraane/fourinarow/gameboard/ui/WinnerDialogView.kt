package dev.jamiecraane.fourinarow.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.fourinarow.common.ui.components.core.ActionButton
import dev.jamiecraane.fourinarow.common.ui.theme.FourInARowTheme
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.MainViewController
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.WinnerViewModel

/**
 * Displayed when there is a winner or the game has ended with a draw.
 */
@Composable
fun WinnerDialogView(
    winner: WinnerViewModel,
    mainViewController: MainViewController,
) {
    WinnerDialogContent(
        winner = winner,
        onCloseClicked = {
            mainViewController.reset()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WinnerDialogContent(
    winner: WinnerViewModel,
    onCloseClicked: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.padding(12.dp).defaultMinSize(300.dp, 140.dp),
        onDismissRequest = { onCloseClicked() },
        title = {
            Column {
                Text(text = "We have a winner!")
                Text(text = winner.name)
            }
        },
        buttons = {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(label = "Close", onClick = {
                    onCloseClicked()
                })
            }
        }
    )
}

@Composable
@Preview
private fun WinnerDialogContentPreview() {
    FourInARowTheme {
        WinnerDialogContent(winner = WinnerViewModel(name = "Jan"), onCloseClicked = {})
    }
}
