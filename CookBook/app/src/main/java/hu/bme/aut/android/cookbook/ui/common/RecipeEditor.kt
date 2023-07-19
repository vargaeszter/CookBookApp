package hu.bme.aut.android.cookbook.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.cookbook.R
import hu.bme.aut.android.cookbook.domain.model.Category
import hu.bme.aut.android.cookbook.ui.model.CategoryUi
import hu.bme.aut.android.cookbook.ui.model.asCategoryUi
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun RecipeEditor(
    titleValue: String,
    titleOnValueChange: (String) -> Unit,
    descriptionValue: String,
    ingredientsValue: String,
    descriptionOnValueChange: (String) -> Unit,
    ingredientsOnValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    categories: List<CategoryUi> = Category.categories.map { it.asCategoryUi() },
    selectedCategory: CategoryUi,
    onCategorySelected: (CategoryUi) -> Unit,
    createDate: LocalDate,
    enabled: Boolean = true,
) {
    val fraction = 0.95f

    val keyboardController = LocalSoftwareKeyboardController.current

    val scroll_ingredients = rememberScrollState(0)
    val scroll_description = rememberScrollState(0)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        if (enabled) {
            NormalTextField(
                value = titleValue,
                label = stringResource(id = R.string.textfield_label_title),
                onValueChange = titleOnValueChange,
                singleLine = true,
                onDone = { keyboardController?.hide()  },
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(fraction)
                    .padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        CategoryDropDown(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected,
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(fraction),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))

        NormalTextField(
            value = ingredientsValue,
            label = stringResource(id = R.string.textfield_label_ingredients),
            onValueChange = ingredientsOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .weight(6f)
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp)
                .verticalScroll(scroll_ingredients),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))
        NormalTextField(
            value = descriptionValue,
            label = stringResource(id = R.string.textfield_label_description),
            onValueChange = descriptionOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .weight(14f)
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp)
                .verticalScroll(scroll_description),
            enabled = enabled
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
@Preview(showBackground = true)
fun RecipeEditor_Preview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }

    val categories = listOf(CategoryUi.Cake, CategoryUi.Beverage, CategoryUi.Coffee, CategoryUi.Snack, CategoryUi.Cocktail)
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val c = LocalDateTime.now()
    var editDate by remember { mutableStateOf(LocalDate(c.year,c.month,c.dayOfMonth)) }

    Box(Modifier.fillMaxSize()) {
        RecipeEditor(
            titleValue = title,
            titleOnValueChange = { title = it },
            descriptionValue = description,
            descriptionOnValueChange = { description = it },
            ingredientsValue = ingredients,
            ingredientsOnValueChange = { ingredients = it },
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            createDate = editDate,
        )

    }
}