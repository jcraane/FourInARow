package dev.jamiecraane.fourinarow.gameboard.viewcontroller

import androidx.compose.ui.graphics.Color
import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import dev.jamiecraane.fourinarow.extensions.color
import dev.jamiecraane.fourinarow.gameboard.domain.Piece
import dev.jamiecraane.fourinarow.settings.domain.Settings
import dev.jamiecraane.fourinarow.settings.repository.SettingsRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewControllerTest {
    @MockK
    lateinit var settingsRepository: SettingsRepository

    private val settings = Settings(playerOne = "John Smith", playerTwo = "Jane Doe")

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { settingsRepository.retrieveSettings() } returns settings
    }

    @Test
    fun startNewGame() = runTest {
        val mainViewController = getMainViewController()
        mainViewController.startNewGame(Piece.RED, Piece.YELLOW)

        launch {
            mainViewController.mainScreenState.test {
                val item = awaitItem()
                assertFalse(item.showSettings)
                assertFalse(item.showNewGame)
                assertNull(item.winner)
                assertTrue(item.playedPieces.isEmpty())
                assertEquals(settings.playerOne, item.whoIsNext?.name)
                assertEquals(Piece.RED, item.whoIsNext?.piece)

                cancelAndCleanup(mainViewController)
            }
        }
    }

    private fun TestScope.getMainViewController() =
        MainViewController(settingsRepository).apply {
            init(this@getMainViewController)
        }


    private suspend fun FlowTurbine<MainScreenViewModel>.cancelAndCleanup(
        mainViewController: MainViewController,
    ) {
        mainViewController.stopTimer()
        cancelAndIgnoreRemainingEvents()
    }

    @Test
    fun playPiece() = runTest {
        val mainViewController = getMainViewController()
        mainViewController.startNewGame(Piece.RED, Piece.YELLOW)
        mainViewController.playPiece(3)

        launch {
            mainViewController.mainScreenState.test {
                val item = awaitItem()
                assertFalse(item.showSettings)
                assertFalse(item.showNewGame)
                assertNull(item.winner)
                assertEquals(PieceViewModel(Piece.RED.color, 3, 5), item.playedPieces.first())
                assertEquals(settings.playerTwo, item.whoIsNext?.name)
                assertEquals(Piece.YELLOW, item.whoIsNext?.piece)

                cancelAndCleanup(mainViewController)
            }
        }
    }
}
