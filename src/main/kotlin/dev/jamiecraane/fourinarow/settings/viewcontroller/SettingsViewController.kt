package dev.jamiecraane.fourinarow.settings.viewcontroller

import dev.jamiecraane.fourinarow.settings.domain.Settings
import dev.jamiecraane.fourinarow.settings.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope

class SettingsViewController(private val settingsRepository: SettingsRepository) {
    private lateinit var viewModelScope: CoroutineScope

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    fun save(settings: Settings) {
        settingsRepository.saveSettings(settings)
    }

    fun retrieveSettings() = settingsRepository.retrieveSettings()
}
