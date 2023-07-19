package hu.bme.aut.android.cookbook.feature.recipe_list

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.cookbook.R
import hu.bme.aut.android.cookbook.ui.common.MainAppBar
import hu.bme.aut.android.cookbook.ui.model.toUiText
import hu.bme.aut.android.cookbook.datastore.CookBookSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun RecipesScreen(
    onListItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: RecipesViewModel = viewModel(factory = RecipesViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val store = CookBookSettings(context)

    val appTheme = store.getAppTheme.collectAsState(initial = "dark")

    var localTheme = appTheme.value


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!state.isLoading ) {
                MainAppBar(
                    title = stringResource(id = R.string.text_your_recipe_list),
                    actions = {

                            IconButton(
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        store.saveAppTheme(localTheme)
                                    }
                                }
                            ) {
                                if (localTheme == "dark") {
                                    Icon(
                                        imageVector = Icons.Default.LightMode,
                                        contentDescription = null
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.DarkMode,
                                        contentDescription = null
                                    )
                                }

                        }
                        IconButton(
                            onClick = {
                                viewModel.onDeleteEvent()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                )
            }
        },
        floatingActionButton = {
                LargeFloatingActionButton(
                    onClick = onFabClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(75.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer
                ),
            contentAlignment = Alignment.Center
        ) {

                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                } else if (state.isError) {
                    Text(
                        text = state.error?.toUiText()?.asString(context)
                            ?: stringResource(id = R.string.some_error_message)
                    )
                }
                else {
                    if (state.recipes.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.text_empty_recipe_list),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.text_your_recipe_list),
                            color = MaterialTheme.colorScheme.onSecondary
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(0.98f)
                                .padding(it)
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            items(state.recipes.size) { i ->
                                ListItem(
                                    headlineText = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = state.recipes[i].title,
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )
                                            Icon(
                                                imageVector = state.recipes[i].category.imagevector,
                                                contentDescription = null,
                                                tint = state.recipes[i].category.color,
                                                modifier = Modifier
                                                    .size(22.dp)
                                                    .padding(start = 10.dp),
                                            )
                                        }
                                    },
                                    supportingText = {
                                        Text(
                                            text = stringResource(
                                                id = R.string.list_item_supporting_text,
                                                state.recipes[i].createDate
                                            ),
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                    },
                                    modifier = Modifier
                                        .combinedClickable(
                                            onClick = { onListItemClick(state.recipes[i].id)  },
                                            onLongClick = {
                                                val sendIntent: Intent = Intent().apply {
                                                    action = Intent.ACTION_SEND
                                                    putExtra(Intent.EXTRA_TEXT, "Recipe: " + state.recipes[i].title + "\nCategrory: " + state.recipes[i].category.name + "\nIngredinets: " + state.recipes[i].ingredients + "\nPreparation: " + state.recipes[i].description)
                                                    type = "text/plain"
                                                }

                                                val shareIntent = Intent.createChooser(sendIntent, null)
                                                context.startActivity(shareIntent)
                                            }
                                        )
                                        .clip(RoundedCornerShape(5.dp))
                                )
                                if (i != state.recipes.lastIndex) {
                                    Divider(
                                        thickness = 2.dp,
                                        color = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }
}