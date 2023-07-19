package hu.bme.aut.android.cookbook.data.repository

import hu.bme.aut.android.cookbook.data.dao.RecipeDao
import hu.bme.aut.android.cookbook.data.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(private val dao: RecipeDao) : RecipeRepository {

    override fun getAllRecipes(): Flow<List<RecipeEntity>> = dao.getAllRecipes()

    override fun getRecipeById(id: Int): Flow<RecipeEntity> = dao.getRecipeById(id)

    override suspend fun insertRecipe(recipe: RecipeEntity) {
        dao.insertRecipe(recipe)
    }

    override suspend fun updateRecipe(recipe: RecipeEntity) {
        dao.updateRecipe(recipe)
    }

    override suspend fun deleteRecipe(id: Int) {
        dao.deleteRecipe(id)
    }

    override suspend fun deleteAllRecipes() {
        dao.deleteAllRecipes()
    }
}