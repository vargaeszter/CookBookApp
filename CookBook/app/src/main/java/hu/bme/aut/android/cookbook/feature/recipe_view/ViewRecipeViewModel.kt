package hu.bme.aut.android.cookbook.feature.recipe_view

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.cookbook.CookBookApplication
import hu.bme.aut.android.cookbook.domain.usecases.RecipeUseCases
import hu.bme.aut.android.cookbook.ui.model.UiEvent
import hu.bme.aut.android.cookbook.ui.model.asRecipe
import hu.bme.aut.android.cookbook.ui.model.asRecipeUi
import hu.bme.aut.android.cookbook.ui.model.toUiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewRecipeViewModel(
    private val savedState: SavedStateHandle,
    private val recipeOperations: RecipeUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewRecipeState())
    val state: StateFlow<ViewRecipeState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: ViewRecipeEvent) {
        when(event) {
            ViewRecipeEvent.EditingRecipe -> {
                _state.update { it.copy(
                    isEditingRecipe = true
                ) }
            }
            ViewRecipeEvent.StopEditingRecipe -> {
                _state.update { it.copy(
                    isEditingRecipe = false
                ) }
            }
            is ViewRecipeEvent.ChangeTitle -> {
                val newValue = event.text
                _state.update { it.copy(
                    recipe = it.recipe?.copy(title = newValue)
                ) }
            }
            is ViewRecipeEvent.ChangeDescription -> {
                val newValue = event.text
                _state.update { it.copy(
                    recipe = it.recipe?.copy(description = newValue)
                ) }
            }
            is ViewRecipeEvent.ChangeIngredients -> {
                val newValue = event.text
                _state.update { it.copy(
                    recipe = it.recipe?.copy(ingredients = newValue)
                ) }
            }
            is ViewRecipeEvent.SelectCategory -> {
                val newValue = event.category
                _state.update { it.copy(
                    recipe = it.recipe?.copy(category = newValue)
                ) }
            }
            ViewRecipeEvent.DeleteRecipe -> {
                onDelete()
            }
            ViewRecipeEvent.UpdateRecipe -> {
                onUpdate()
            }
        }
    }

    init {
        load()
    }

    private fun load() {
        val recipeId = checkNotNull<Int>(savedState["id"])
        viewModelScope.launch {
            _state.update { it.copy(isLoadingRecipe = true) }
            try {
                val recipe = recipeOperations.loadRecipe(recipeId)
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    _state.update { it.copy(
                        isLoadingRecipe = false,
                        recipe = recipe.getOrThrow().asRecipeUi()
                    ) }
                }
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recipeOperations.updateRecipe(
                    _state.value.recipe?.asRecipe()!!
                )
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            try {
                recipeOperations.deleteRecipe(state.value.recipe!!.id)
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val recipeOperations = RecipeUseCases(CookBookApplication.repository)
                ViewRecipeViewModel(
                    savedState = savedStateHandle,
                    recipeOperations = recipeOperations
                )
            }
        }
    }
}