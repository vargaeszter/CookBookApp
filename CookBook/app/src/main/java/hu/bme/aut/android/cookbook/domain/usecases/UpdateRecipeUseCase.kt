package hu.bme.aut.android.cookbook.domain.usecases

import hu.bme.aut.android.cookbook.domain.model.Recipe
import hu.bme.aut.android.cookbook.domain.model.asRecipeEntity
import hu.bme.aut.android.cookbook.data.repository.RecipeRepository

class UpdateRecipeUseCase(private val repository: RecipeRepository) {

    suspend operator fun invoke(recipe: Recipe) {
        repository.updateRecipe(recipe.asRecipeEntity())
    }

}