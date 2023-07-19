package hu.bme.aut.android.cookbook.feature.recipe_list

import hu.bme.aut.android.cookbook.ui.model.RecipeUi

data class RecipesState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val recipes: List<RecipeUi> = emptyList()
)