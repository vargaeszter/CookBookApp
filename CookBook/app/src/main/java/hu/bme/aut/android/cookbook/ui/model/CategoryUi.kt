package hu.bme.aut.android.cookbook.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.ui.graphics.vector.ImageVector
import hu.bme.aut.android.cookbook.R
import hu.bme.aut.android.cookbook.domain.model.Category

enum class CategoryUi(
    val title: Int,
    val color: Color,
    val imagevector: ImageVector
) {
    None(
        title =  R.string.category_name_none,
        color = Color(0xFFE4E4E4),
        imagevector = Icons.Default.Circle
    ),
    Cake(
        title = R.string.category_name_cake,
        color = Color(0xFFFC9EFF),
        imagevector = Icons.Default.Cake
    ),
    Beverage(
        title = R.string.category_name_beverage,
        color = Color(0xFFFB6654),
        imagevector = Icons.Default.EmojiFoodBeverage
    ),
    Coffee(
        title = R.string.category_name_coffee,
        color = Color(0xFF896050),
        imagevector = Icons.Default.Coffee
    ),
    Snack(
        title =  R.string.category_name_snack,
        color = Color(0xFFFFC107),
        imagevector = Icons.Default.LunchDining
    ),
    Cocktail(
        title =  R.string.category_name_cocktail,
        color = Color(0xFF54DBFB),
        imagevector = Icons.Default.LocalBar
    ),
}

fun CategoryUi.asCategory(): Category {
    return when(this) {
        CategoryUi.None -> Category.NONE
        CategoryUi.Cake -> Category.CAKE
        CategoryUi.Beverage -> Category.BEVERAGE
        CategoryUi.Coffee -> Category.COFFEE
        CategoryUi.Snack -> Category.SNACK
        CategoryUi.Cocktail -> Category.COCKTAIL
    }
}

fun Category.asCategoryUi(): CategoryUi {
    return when(this) {
        Category.NONE -> CategoryUi.None
        Category.CAKE -> CategoryUi.Cake
        Category.BEVERAGE -> CategoryUi.Beverage
        Category.COFFEE -> CategoryUi.Coffee
        Category.SNACK -> CategoryUi.Snack
        Category.COCKTAIL -> CategoryUi.Cocktail
    }
}