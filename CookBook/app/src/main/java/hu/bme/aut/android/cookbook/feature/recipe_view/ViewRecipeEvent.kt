package hu.bme.aut.android.cookbook.feature.recipe_view

import hu.bme.aut.android.cookbook.ui.model.CategoryUi

sealed class ViewRecipeEvent {
    object EditingRecipe: ViewRecipeEvent()
    object StopEditingRecipe: ViewRecipeEvent()
    data class ChangeTitle(val text: String): ViewRecipeEvent()
    data class ChangeDescription(val text: String): ViewRecipeEvent()
    data class ChangeIngredients(val text: String): ViewRecipeEvent()
    data class SelectCategory(val category: CategoryUi): ViewRecipeEvent()
    object DeleteRecipe: ViewRecipeEvent()
    object UpdateRecipe: ViewRecipeEvent()
}