package dev.jamiecraane.fourinarow.settings.repository

import dev.jamiecraane.fourinarow.settings.domain.Settings

interface SettingsRepository {
    fun saveSettings(settings: Settings)
    fun retrieveSettings(): Settings
}
