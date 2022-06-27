package dev.jamiecraane.util

import dev.jamiecraane.domain.FourInARow
import kotlinx.coroutines.CoroutineScope

open class ViewModel {
    private lateinit var viewModelScope: CoroutineScope

    var gameBoard = FourInARow()
        private set

    open fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    fun newGame() {
        this.gameBoard = FourInARow()
    }
}
