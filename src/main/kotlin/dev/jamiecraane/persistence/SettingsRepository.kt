package dev.jamiecraane.persistence

import dev.jamiecraane.domain.Settings

interface SettingsRepository {
    fun saveSettings(settings: Settings)
    fun retrieveSettings(): Settings
}
