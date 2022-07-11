package dev.jamiecraane.fourinarow

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.jamiecraane.fourinarow.settings.repository.InMemorySettingsRepository
import dev.jamiecraane.fourinarow.ui.screens.GameView
import dev.jamiecraane.fourinarow.common.ui.theme.FourInARowTheme
import dev.jamiecraane.fourinarow.gameboard.viewcontroller.MainViewController
import dev.jamiecraane.fourinarow.settings.viewcontroller.SettingsViewController

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    val settingsRepository = InMemorySettingsRepository()
    val mainViewController = MainViewController(settingsRepository).apply {
        init(scope)
    }
    val settingsViewController = SettingsViewController(settingsRepository).apply {
        init(scope)
    }

    FourInARowTheme {
        GameView(mainViewController, settingsViewController)
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication, state = WindowState(size = DpSize(800.dp, 700.dp))) {
        App()
    }
}
