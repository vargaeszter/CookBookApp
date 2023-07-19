package hu.bme.aut.android.cookbook.feature.recipe_create

import hu.bme.aut.android.cookbook.ui.model.RecipeUi

data class CreateRecipeState(
    val recipe: RecipeUi = RecipeUi()
)
