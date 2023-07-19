package hu.bme.aut.android.cookbook.data.repository

import hu.bme.aut.android.cookbook.data.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    fun getRecipeById(id: Int): Flow<RecipeEntity>

    suspend fun insertRecipe(recipe: RecipeEntity)

    suspend fun updateRecipe(recipe: RecipeEntity)

    suspend fun deleteRecipe(id: Int)

    suspend fun deleteAllRecipes()
}

