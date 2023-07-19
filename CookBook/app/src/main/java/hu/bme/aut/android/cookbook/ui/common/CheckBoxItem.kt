package hu.bme.aut.android.cookbook.ui.common

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun SimpleCheckbox() {
    val isChecked = remember { mutableStateOf(false) }
    Checkbox(checked = isChecked.value, onCheckedChange = { isChecked.value = it })
}