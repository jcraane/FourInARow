package dev.jamiecraane.viewcontroller.settings

import dev.jamiecraane.persistence.InMemorySettingsRepository
import dev.jamiecraane.persistence.SettingsRepository
import kotlinx.coroutines.CoroutineScope

class SettingsViewController(private val settingsRepository: SettingsRepository) {
    private lateinit var viewModelScope: CoroutineScope

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }
}
