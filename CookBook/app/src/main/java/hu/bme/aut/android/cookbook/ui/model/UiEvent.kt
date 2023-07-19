package hu.bme.aut.android.cookbook.ui.model

sealed class UiEvent {
    object Success: UiEvent()
    data class Failure(val message: UiText): UiEvent()
}