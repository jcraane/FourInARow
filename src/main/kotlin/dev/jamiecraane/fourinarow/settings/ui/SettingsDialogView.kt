package dev.jamiecraane.fourinarow.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.fourinarow.settings.domain.Settings
import dev.jamiecraane.fourinarow.common.ui.components.core.ActionButton
import dev.jamiecraane.fourinarow.common.ui.theme.FourInARowTheme
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.MainViewController
import dev.jamiecraane.fourinarow.settings.viewcontroller.SettingsViewController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsDialogView(
    mainViewController: MainViewController,
    settingsViewController: SettingsViewController,
) {
    SettingsDialogContent(
        onCloseClicked = { mainViewController.closeSettingsDialog() },
        onSaveClicked = {
            settingsViewController.save(it)
            mainViewController.closeSettingsDialog()
        },
        initialSettings = settingsViewController.retrieveSettings(),
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsDialogContent(
    initialSettings: Settings,
    onCloseClicked: () -> Unit,
    onSaveClicked: (Settings) -> Unit,
) {
    var namePlayerOne by remember { mutableStateOf(initialSettings.playerOne) }
    var namePlayerTwo by remember { mutableStateOf(initialSettings.playerTwo) }

    AlertDialog(
        modifier = Modifier.padding(12.dp),
        onDismissRequest = { onCloseClicked() },
        title = {
            Text(text = "Settings")
        },
        text = {
//            todo add color with which the players will play here instead of new game
            Column() {
                Row() {
                    Text(text = "Player one", modifier = Modifier.defaultMinSize(140.dp))
                    TextField(value = namePlayerOne, onValueChange = { namePlayerOne = it })
                }

                Row() {
                    Text(text = "Player two", modifier = Modifier.defaultMinSize(140.dp))
                    TextField(value = namePlayerTwo, onValueChange = { namePlayerTwo = it })
                }

            }
        },
        buttons = {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(label = "Save", onClick = {
                    onSaveClicked(Settings(playerOne = namePlayerOne, playerTwo = namePlayerTwo))
                })
                ActionButton(label = "Cancel", onClick = {
                    onCloseClicked()
                })
            }
        }
    )

}

@Composable
@Preview
private fun SettingsDialogContentPreview() {
    FourInARowTheme {
        SettingsDialogContent(onCloseClicked = {}, onSaveClicked = { settings -> }, initialSettings = Settings("PL1", "PL2"))
    }
}
