package hu.bme.aut.android.cookbook.feature.recipe_create

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.cookbook.ui.common.RecipeAppBar
import hu.bme.aut.android.cookbook.ui.model.UiEvent
import hu.bme.aut.android.cookbook.ui.common.RecipeEditor
import hu.bme.aut.android.cookbook.R
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun CreateRecipeScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreateRecipeViewModel = viewModel(factory = CreateRecipeViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
                is UiEvent.Success -> { onNavigateBack() }
                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            RecipeAppBar(
                title = stringResource(id = R.string.app_bar_title_create_recipe),
                onNavigateBack = onNavigateBack,
                actions = { }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { viewModel.onEvent(CreateRecipeEvent.SaveRecipe) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(75.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            RecipeEditor(
                titleValue = state.recipe.title,
                titleOnValueChange = { viewModel.onEvent(CreateRecipeEvent.ChangeTitle(it)) },
                descriptionValue = state.recipe.description,
                descriptionOnValueChange = { viewModel.onEvent(CreateRecipeEvent.ChangeDescription(it)) },
                ingredientsValue = state.recipe.ingredients,
                ingredientsOnValueChange = { viewModel.onEvent(CreateRecipeEvent.ChangeIngredients(it)) },
                selectedCategory = state.recipe.category,
                onCategorySelected = { viewModel.onEvent(CreateRecipeEvent.SelectCategory(it)) },
                createDate = state.recipe.createDate.toLocalDate(),
                modifier = Modifier
            )
        }
    }
}