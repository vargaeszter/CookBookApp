package hu.bme.aut.android.cookbook.feature.recipe_view

import hu.bme.aut.android.cookbook.ui.model.RecipeUi

data class ViewRecipeState(
    val recipe: RecipeUi? = null,
    val isLoadingRecipe: Boolean = false,
    val isEditingRecipe: Boolean = false,
    val error: Throwable? = null
)