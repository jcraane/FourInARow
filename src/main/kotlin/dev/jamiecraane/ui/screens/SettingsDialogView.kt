package dev.jamiecraane.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.ui.components.core.ActionButton
import dev.jamiecraane.ui.theme.FourInARowTheme
import dev.jamiecraane.viewcontroller.MainViewController
import dev.jamiecraane.viewcontroller.SettingsViewController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsDialogView(
    mainViewController: MainViewController,
    settingsViewController: SettingsViewController,
) {
    SettingsDialogContent(onCloseClicked = { mainViewController.closeSettings() })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SettingsDialogContent(
    onCloseClicked: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.padding(12.dp),
        onDismissRequest = { onCloseClicked() },
        title = {
            Text(text = "Settings")
        },
        buttons = {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionButton(label = "Save", onClick = {

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
        SettingsDialogContent(onCloseClicked = {})
    }
}
