package hu.bme.aut.android.cookbook.data.dao

import androidx.room.*
import hu.bme.aut.android.cookbook.data.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    //TODO update to valid operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    fun getRecipeById(id: Int): Flow<RecipeEntity>

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipe_table WHERE id = :id")
    suspend fun deleteRecipe(id: Int)

    @Query("DELETE from recipe_table")
    suspend fun deleteAllRecipes()



}