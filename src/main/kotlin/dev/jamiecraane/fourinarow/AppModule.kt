package dev.jamiecraane.fourinarow

import dev.jamiecraane.fourinarow.gameboard.viewcontroller.MainViewController
import dev.jamiecraane.fourinarow.settings.repository.InMemorySettingsRepository
import dev.jamiecraane.fourinarow.settings.repository.SettingsRepository
import dev.jamiecraane.fourinarow.settings.viewcontroller.SettingsViewController
import org.koin.dsl.module

val appModule = module {
    single<SettingsRepository> {
        InMemorySettingsRepository()
    }

    single {
        MainViewController(settingsRepository = get(), viewModelScope = it.get())
    }

    single {
        SettingsViewController(settingsRepository = get(), viewModelScope = it.get())
    }
}
