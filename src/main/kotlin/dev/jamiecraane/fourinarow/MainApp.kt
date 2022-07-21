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
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module

@Composable
@Preview
fun App(mainViewController: MainViewController, settingsViewController: SettingsViewController) {
    FourInARowTheme {
        GameView(mainViewController, settingsViewController)
    }
}

fun main(): Unit {
    KoinApp().app()
}

private class KoinApp : KoinComponent {
    fun app() = application {
        startKoin {
            modules(appModule)
        }

        val scope = rememberCoroutineScope()
        val mainViewController by inject<MainViewController> { parametersOf(scope) }
        val settingsViewController by inject<SettingsViewController> { parametersOf(scope) }

        Window(onCloseRequest = ::exitApplication, state = WindowState(size = DpSize(800.dp, 700.dp))) {
            App(mainViewController, settingsViewController)
        }
    }
}
