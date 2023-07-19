package hu.bme.aut.android.cookbook.data.conventers

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.cookbook.data.dao.RecipeDao
import hu.bme.aut.android.cookbook.data.entities.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(RecipeCategoryConverter::class, LocalDateConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val dao: RecipeDao
}