package dev.jamiecraane.util

import dev.jamiecraane.domain.FourInARow
import kotlinx.coroutines.CoroutineScope

open class ViewModel {
    private lateinit var viewModelScope: CoroutineScope

    private var gameBoard = FourInARow()

    open fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    fun newGame() {
        this.gameBoard = FourInARow()
    }
}
