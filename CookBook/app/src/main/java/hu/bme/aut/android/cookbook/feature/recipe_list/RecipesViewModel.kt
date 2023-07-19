package hu.bme.aut.android.cookbook.feature.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.cookbook.CookBookApplication
import hu.bme.aut.android.cookbook.domain.usecases.RecipeUseCases
import hu.bme.aut.android.cookbook.ui.model.asRecipeUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesViewModel(
    private val recipeOperations: RecipeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(RecipesState())
    val state = _state.asStateFlow()

    init {
        loadRecipes()
    }
    private fun loadRecipes() {

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val recipes = recipeOperations.loadRecipes().getOrThrow().map { it.asRecipeUi() }
                    _state.update { it.copy(
                        isLoading = false,
                        recipes = recipes
                    ) }
                }
            } catch (e: Exception) {
                _state.update {  it.copy(
                    isLoading = false,
                    error = e
                ) }
            }
        }
    }

    fun onDeleteEvent() {
        deleteAllRecepies()
        loadRecipes()
    }

    fun deleteAllRecepies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                CookBookApplication.repository.deleteAllRecipes()
                //call dao delete query
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val recipeOperations = RecipeUseCases(CookBookApplication.repository)
                RecipesViewModel(
                    recipeOperations = recipeOperations
                )
            }
        }
    }
}