package dev.jamiecraane.viewcontroller

import kotlinx.coroutines.CoroutineScope

class SettingsViewController {
    private lateinit var viewModelScope: CoroutineScope

    fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }
}
