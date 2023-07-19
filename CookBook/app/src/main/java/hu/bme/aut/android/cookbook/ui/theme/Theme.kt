package hu.bme.aut.android.cookbook.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import hu.bme.aut.android.cookbook.datastore.CookBookSettings

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue80,
    secondary = DarkBlueGrey80,
    tertiary = LightBlue80,
    onPrimary = Color.White,
    secondaryContainer = BlueGrey80,
    onSecondary = Color.White,
    onTertiary = LightGrey,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue20,
    secondary = LightGrey,
    tertiary = LightBlue20,
    onPrimary = Color.White,

    onPrimaryContainer = Color.Black,

    onSecondary = LightBlue20,
    onTertiary = LightBlue20,
    onSurfaceVariant = LightBlue20,

    onSurface= DarkBlue80,

    background = Color.White,
    onBackground = DarkBlueGrey80,
    surface = Color.White,
    secondaryContainer= LightGrey,

    //primary = Purple40,
    //secondary = PurpleGrey40,
    //tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CookBookTheme(
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val store = CookBookSettings(context)
    val appTheme = store.getAppTheme.collectAsState(initial = "dark")
    var localTheme = appTheme.value

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (localTheme == "dark") dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        (localTheme == "dark") -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyAppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(
        colorScheme = colorScheme,
        // shapes = ...,
        // typography = ...,
        content = content
    )
}