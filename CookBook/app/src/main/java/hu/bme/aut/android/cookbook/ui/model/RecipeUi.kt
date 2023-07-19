package hu.bme.aut.android.cookbook.ui.model

import android.graphics.Bitmap
import hu.bme.aut.android.cookbook.domain.model.Category
import hu.bme.aut.android.cookbook.domain.model.Recipe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.time.LocalDateTime

data class RecipeUi(
    val id: Int = 0,
    val title: String = "",
    val category: CategoryUi = CategoryUi.None,
    val createDate: String = LocalDate(
        LocalDateTime.now().year,
        LocalDateTime.now().monthValue,
        LocalDateTime.now().dayOfMonth
    ).toString(),
    val ingredients: String = "",
    val description: String = "",
)

fun Recipe.asRecipeUi(): RecipeUi = RecipeUi(
    id = id,
    title = title,
    category = category.asCategoryUi(),
    createDate = createDate.toString(),
    ingredients = ingredients,
    description = description,
)

fun RecipeUi.asRecipe(): Recipe = Recipe(
    id = id,
    title = title,
    category = category.asCategory(),
    createDate = createDate.toLocalDate(),
    ingredients = ingredients,
    description = description,
)