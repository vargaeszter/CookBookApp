package hu.bme.aut.android.cookbook.domain.usecases

import hu.bme.aut.android.cookbook.domain.model.Recipe
import hu.bme.aut.android.cookbook.data.repository.RecipeRepository
import hu.bme.aut.android.cookbook.domain.model.asRecipe
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadRecipeUseCase(private val repository: RecipeRepository) {

    suspend operator fun invoke(id: Int): Result<Recipe> {
        return try {
            Result.success(repository.getRecipeById(id).first().asRecipe())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}