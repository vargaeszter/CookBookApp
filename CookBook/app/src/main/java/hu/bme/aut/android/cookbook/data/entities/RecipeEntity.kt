package hu.bme.aut.android.cookbook.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.cookbook.domain.model.Category
import kotlinx.datetime.LocalDate

@Entity(tableName = "recipe_table")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val category: Category,
    val createDate: LocalDate,
    val ingredients: String,
    val description: String,
)