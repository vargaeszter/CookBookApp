package hu.bme.aut.android.cookbook.domain.model

import android.graphics.Bitmap
import hu.bme.aut.android.cookbook.data.entities.RecipeEntity
import kotlinx.datetime.LocalDate

data class Recipe(
    val id: Int,
    val title: String,
    val category: Category,
    val createDate: LocalDate,
    val ingredients: String,
    val description: String,
)

fun RecipeEntity.asRecipe(): Recipe = Recipe(
    id = id,
    title = title,
    category = category,
    createDate = createDate,
    ingredients = ingredients,
    description = description,
)

fun Recipe.asRecipeEntity(): RecipeEntity = RecipeEntity(
    id = id,
    title = title,
    category = category,
    createDate = createDate,
    ingredients = ingredients,
    description = description,
)