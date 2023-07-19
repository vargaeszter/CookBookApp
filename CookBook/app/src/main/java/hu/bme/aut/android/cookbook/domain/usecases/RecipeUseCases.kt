package hu.bme.aut.android.cookbook.domain.usecases

import hu.bme.aut.android.cookbook.data.repository.RecipeRepository

class RecipeUseCases(repository: RecipeRepository) {
    val loadRecipes = LoadRecipesUseCase(repository)
    val loadRecipe = LoadRecipeUseCase(repository)
    val saveRecipe = SaveRecipeUseCase(repository)
    val updateRecipe = UpdateRecipeUseCase(repository)
    val deleteRecipe = DeleteRecipeUseCase(repository)
}