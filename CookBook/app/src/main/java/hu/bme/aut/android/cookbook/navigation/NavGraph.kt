package hu.bme.aut.android.cookbook.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.cookbook.feature.recipe_view.ViewRecipeScreen
import hu.bme.aut.android.cookbook.feature.recipe_create.CreateRecipeScreen
import hu.bme.aut.android.cookbook.feature.recipe_list.RecipesScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Recipes.route
    ) {
        composable(Screen.Recipes.route) {
            RecipesScreen(
                onListItemClick = {
                    navController.navigate(Screen.ViewRecipe.passId(it))
                },
                onFabClick = {
                    navController.navigate(Screen.CreateRecipe.route)
                },
            )
        }
        composable(Screen.CreateRecipe.route) {
            CreateRecipeScreen(onNavigateBack = {
                navController.popBackStack(
                    route = Screen.Recipes.route,
                    inclusive = true
                )
                navController.navigate(Screen.Recipes.route)
            })
        }
        composable(
            route = Screen.ViewRecipe.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            ViewRecipeScreen(
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.Recipes.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Recipes.route)
                }
            )
        }
    }
}