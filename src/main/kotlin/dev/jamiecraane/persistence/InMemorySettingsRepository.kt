package dev.jamiecraane.persistence

import dev.jamiecraane.domain.Settings

class InMemorySettingsRepository : SettingsRepository {
    private var settings: Settings = Settings()

    override fun saveSettings(settings: Settings) {
        this.settings = settings
    }

    override fun retrieveSettings() = settings
}
