package dev.jamiecraane.fourinarow.settings.repository

import dev.jamiecraane.fourinarow.settings.domain.Settings

class InMemorySettingsRepository : SettingsRepository {
    private var settings: Settings = Settings()

    override fun saveSettings(settings: Settings) {
        this.settings = settings
    }

    override fun retrieveSettings() = settings
}
