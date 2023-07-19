package hu.bme.aut.android.cookbook.data.conventers

import androidx.room.TypeConverter
import hu.bme.aut.android.cookbook.domain.model.Category

object RecipeCategoryConverter {

    @TypeConverter
    fun Category.asString(): String = this.name

    @TypeConverter
    fun String.asCategory(): Category {
        return when(this) {
            Category.NONE.name -> Category.NONE
            Category.CAKE.name -> Category.CAKE
            Category.BEVERAGE.name -> Category.BEVERAGE
            Category.COFFEE.name -> Category.COFFEE
            Category.SNACK.name -> Category.SNACK
            Category.COCKTAIL.name -> Category.COCKTAIL
            else -> Category.NONE
        }
    }
}