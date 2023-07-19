package hu.bme.aut.android.cookbook.feature.recipe_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.cookbook.R
import hu.bme.aut.android.cookbook.anim.LoadingAnimation
import hu.bme.aut.android.cookbook.ui.common.MainAppBar
import hu.bme.aut.android.cookbook.ui.common.RecipeAppBar
import hu.bme.aut.android.cookbook.ui.common.RecipeEditor
import hu.bme.aut.android.cookbook.ui.common.SimpleCheckbox
import hu.bme.aut.android.cookbook.ui.model.RecipeUi
import hu.bme.aut.android.cookbook.ui.model.UiEvent
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun ViewRecipeScreen(
    onNavigateBack: () -> Unit,
    viewModel: ViewRecipeViewModel = viewModel(factory = ViewRecipeViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var isShoppingList by remember {mutableStateOf(false)}

    val delim = ","

    var listOfIngredients by remember{ mutableStateOf(listOf(""))}

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> { onNavigateBack() }
                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }

    }

    if(isShoppingList){
        Scaffold(
            snackbarHost = { SnackbarHost(hostState) },
            topBar = {
                if (!state.isLoadingRecipe) {
                    MainAppBar(
                        title = if (state.isEditingRecipe) {
                            stringResource(id = R.string.app_bar_title_edit_recipe)
                        } else state.recipe?.title ?: "Recipe",
                        //onNavigateBack = onNavigateBack,
                        actions = { }
                    )
                }
            },
            floatingActionButton = {
                LargeFloatingActionButton(
                    onClick = {
                        isShoppingList = false
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(75.dp)
                ) {
                    Icon(imageVector = Icons.Default.MenuBook, contentDescription = null)
                }

            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                if (!state.isLoadingRecipe) {
                    listOfIngredients = state.recipe?.ingredients?.split(delim) ?:listOf("")

                    //Text(text = stringResource(id = R.string.text_shopping_list))

                    LazyColumn(
                        modifier = Modifier

                            .clip(RoundedCornerShape(5.dp))
                            .fillMaxSize(0.98f)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(4.dp)
                    ) {
                        items(listOfIngredients.size) { i ->
                            ListItem(
                                headlineText = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        SimpleCheckbox()
                                        Text(text = listOfIngredients[i],
                                            color = MaterialTheme.colorScheme.onSecondary
                                        )

                                    }
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                //modifier = Modifier.clickable(onClick = { onListItemClick(state.recipes[i].id) })
                            )
                            if (i != listOfIngredients.size) {
                                Divider(
                                    thickness = 4.dp,
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                )
                            }
                        }
                    }

                }
                else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        LoadingAnimation()
                    }
                }

            }
        }
    }
    else{

        Scaffold(
            snackbarHost = { SnackbarHost(hostState) },
            topBar = {
                if (!state.isLoadingRecipe) {
                    RecipeAppBar(
                        title = if (state.isEditingRecipe) {
                            stringResource(id = R.string.app_bar_title_edit_recipe)
                        } else state.recipe?.title ?: "Recipe",
                        onNavigateBack = onNavigateBack,
                        actions = {
                            IconButton(
                                onClick = {
                                    if (state.isEditingRecipe) {
                                        viewModel.onEvent(ViewRecipeEvent.StopEditingRecipe)
                                    } else {
                                        viewModel.onEvent(ViewRecipeEvent.EditingRecipe)
                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                            }
                            IconButton(
                                onClick = {
                                    viewModel.onEvent(ViewRecipeEvent.DeleteRecipe)
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                        }
                    )
                }
            },
            floatingActionButton = {
                if (state.isEditingRecipe) {
                    LargeFloatingActionButton(
                        onClick = {
                            viewModel.onEvent(ViewRecipeEvent.UpdateRecipe)
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(75.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    }
                }
                else {
                    LargeFloatingActionButton(
                        onClick = {
                            isShoppingList = true
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(75.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBasket,
                            contentDescription = null
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoadingRecipe) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                } else {
                    val recipe = state.recipe ?: RecipeUi()
                    RecipeEditor(
                        titleValue = recipe.title,
                        titleOnValueChange = { viewModel.onEvent(ViewRecipeEvent.ChangeTitle(it)) },
                        descriptionValue = recipe.description,
                        descriptionOnValueChange = { viewModel.onEvent(ViewRecipeEvent.ChangeDescription(it)) },
                        ingredientsValue = recipe.ingredients,
                        ingredientsOnValueChange = { viewModel.onEvent(ViewRecipeEvent.ChangeIngredients(it)) },
                        selectedCategory = recipe.category,
                        onCategorySelected = { viewModel.onEvent(ViewRecipeEvent.SelectCategory(it)) },
                        createDate = recipe.createDate.toLocalDate(),
                        modifier = Modifier,
                        enabled = state.isEditingRecipe
                    )
                }
            }
        }
    }
}

