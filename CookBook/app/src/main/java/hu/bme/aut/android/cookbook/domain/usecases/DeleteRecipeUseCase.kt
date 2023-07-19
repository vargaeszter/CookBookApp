package hu.bme.aut.android.cookbook.domain.usecases

import hu.bme.aut.android.cookbook.data.repository.RecipeRepository

class DeleteRecipeUseCase(private val repository: RecipeRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteRecipe(id)
    }

}