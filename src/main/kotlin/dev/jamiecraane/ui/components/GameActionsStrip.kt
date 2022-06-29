package dev.jamiecraane.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jamiecraane.ui.components.core.ActionButton
import dev.jamiecraane.ui.theme.FourInARowTheme

@Composable
fun GameActionsStrip(
    onNewGameClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.width(160.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ActionButton(
            label = "New Game",
            onClick = onNewGameClicked,
        )
    }
}

@Composable
@Preview
private fun GameActionsStripPreview() {
    FourInARowTheme {
        GameActionsStrip(onNewGameClicked = {})
    }
}

