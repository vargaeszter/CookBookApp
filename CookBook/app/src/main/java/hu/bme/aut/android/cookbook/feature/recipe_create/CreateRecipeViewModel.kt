package hu.bme.aut.android.cookbook.feature.recipe_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.cookbook.CookBookApplication
import hu.bme.aut.android.cookbook.domain.usecases.RecipeUseCases
import hu.bme.aut.android.cookbook.ui.model.UiEvent
import hu.bme.aut.android.cookbook.ui.model.asRecipe
import hu.bme.aut.android.cookbook.ui.model.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateRecipeViewModel(
    private val recipeOperations: RecipeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CreateRecipeState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateRecipeEvent) {
        when(event) {
            is CreateRecipeEvent.ChangeTitle -> {
                val newValue = event.text
                _state.update { it.copy(
                    recipe = it.recipe.copy(title = newValue)
                ) }
            }
            is CreateRecipeEvent.ChangeDescription -> {
                val newValue = event.text
                _state.update { it.copy(
                    recipe = it.recipe.copy(description = newValue)
                ) }
            }
            is CreateRecipeEvent.ChangeIngredients -> {
                val newValue = event.text
                _state.update { it.copy(
                    recipe = it.recipe.copy(ingredients = newValue)
                ) }
            }
            is CreateRecipeEvent.SelectCategory -> {
                val newValue = event.category
                _state.update { it.copy(
                    recipe = it.recipe.copy(category = newValue)
                ) }
            }
            CreateRecipeEvent.SaveRecipe -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                recipeOperations.saveRecipe(state.value.recipe.asRecipe())
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeOperations = RecipeUseCases(CookBookApplication.repository)
                CreateRecipeViewModel(
                    recipeOperations = recipeOperations
                )
            }
        }
    }

}