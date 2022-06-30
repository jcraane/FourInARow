package dev.jamiecraane.viewcontroller.settings

import kotlinx.coroutines.CoroutineScope

class SettingsViewController {
    private lateinit var viewModelScope: CoroutineScope

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }
}
