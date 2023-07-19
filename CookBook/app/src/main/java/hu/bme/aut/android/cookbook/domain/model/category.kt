package hu.bme.aut.android.cookbook.domain.model

enum class Category {
    NONE,
    CAKE,
    BEVERAGE,
    COFFEE,
    COCKTAIL,
    SNACK;

    companion object {
        val categories = listOf(NONE, CAKE, BEVERAGE, COFFEE, COCKTAIL, SNACK)
    }
}