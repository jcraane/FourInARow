package dev.jamiecraane.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.ui.components.core.ActionButton
import dev.jamiecraane.ui.theme.FourInARowTheme

@Composable
fun ActionStrip(
    onNewGameClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.width(160.dp).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ActionButton(
            modifier = Modifier.fillMaxWidth(),
            label = "New Game",
            onClick = onNewGameClicked,
        )

        ActionButton(
            modifier = Modifier.fillMaxWidth(),
            label = "Settings",
            onClick = onSettingsClicked,
        )
    }
}

@Composable
@Preview
private fun GameActionsStripPreview() {
    FourInARowTheme {
        ActionStrip(onNewGameClicked = {}, onSettingsClicked = {})
    }
}

