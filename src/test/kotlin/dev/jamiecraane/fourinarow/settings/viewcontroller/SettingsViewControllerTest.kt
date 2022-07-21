package dev.jamiecraane.fourinarow.settings.viewcontroller

import dev.jamiecraane.fourinarow.settings.domain.Settings
import dev.jamiecraane.fourinarow.settings.repository.SettingsRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SettingsViewControllerTest {
    @MockK
    lateinit var settingsRepository: SettingsRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
    }

    @Test
    fun retrieveSettings() = runTest{
        val settingsViewController = getSettingsViewController()
        settingsViewController.retrieveSettings()

        verify { settingsRepository.retrieveSettings() }
    }

    @Test
    fun saveSettings() = runTest {
        val settingsViewController = getSettingsViewController()
        val newSettings = Settings(playerTwo = "ONE", playerOne = "TWO")
        settingsViewController.save(newSettings)

        verify { settingsRepository.saveSettings(newSettings) }
    }

    private fun TestScope.getSettingsViewController() =
        SettingsViewController(settingsRepository).apply {
            init(this@getSettingsViewController)
        }
}
