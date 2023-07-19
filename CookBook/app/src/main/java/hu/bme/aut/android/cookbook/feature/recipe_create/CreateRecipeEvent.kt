package hu.bme.aut.android.cookbook.feature.recipe_create

import hu.bme.aut.android.cookbook.ui.model.CategoryUi

sealed class CreateRecipeEvent {
    data class ChangeTitle(val text: String): CreateRecipeEvent()
    data class ChangeDescription(val text: String): CreateRecipeEvent()
    data class ChangeIngredients(val text: String): CreateRecipeEvent()
    data class SelectCategory(val category: CategoryUi): CreateRecipeEvent()
    object SaveRecipe: CreateRecipeEvent()
}