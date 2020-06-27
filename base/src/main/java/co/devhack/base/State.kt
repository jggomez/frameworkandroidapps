package co.devhack.base

import co.devhack.base.error.Failure

sealed class State {
    object Loading : State()
    object Empty : State()
    data class Failed(val failure: Failure) : State()
    data class Success(var data: Any) : State() {
        inline fun <reified T> responseTo() = data as T
    }
}