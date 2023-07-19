package hu.bme.aut.android.cookbook.domain.usecases

import hu.bme.aut.android.cookbook.domain.model.Recipe
import hu.bme.aut.android.cookbook.data.repository.RecipeRepository
import hu.bme.aut.android.cookbook.domain.model.asRecipe
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadRecipesUseCase(private val repository: RecipeRepository) {

    suspend operator fun invoke(): Result<List<Recipe>> {
        return try {
            val recipes = repository.getAllRecipes().first()
            Result.success(recipes.map { it.asRecipe() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}