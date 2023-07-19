package hu.bme.aut.android.cookbook.navigation

sealed class Screen(val route: String) {
    object Recipes: Screen("recipes")
    object CreateRecipe: Screen("create")
    object ViewRecipe: Screen("view/{id}") {
        fun passId(id: Int) = "view/$id"
    }
}