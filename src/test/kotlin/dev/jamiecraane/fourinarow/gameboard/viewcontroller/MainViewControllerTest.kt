package dev.jamiecraane.fourinarow.gameboard.viewcontroller

import app.cash.turbine.test
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

    private val dispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun startNewGame() = runTest(dispatcher) {
        val settings = Settings(playerOne = "John Smith", playerTwo = "Jane Doe")
        every { settingsRepository.retrieveSettings() } returns settings

        val mainViewController = MainViewController(settingsRepository).apply {
            init(this@runTest)
        }

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

                mainViewController.stopTimer()
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
