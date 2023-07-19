package hu.bme.aut.android.cookbook.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CookBookSettings(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        private val APP_THEME = stringPreferencesKey("apptheme")

    }

    val getAppTheme: Flow<String> = context.dataStore.data.map {
            preferences ->
        preferences[APP_THEME] ?: "dark" //dark or light
    }


    suspend fun saveAppTheme(theme: String) {
        if(theme == "dark") {
            context.dataStore.edit { preferences ->
                preferences[APP_THEME] = "light"
            }
        }
        else{
            context.dataStore.edit { preferences ->
                preferences[APP_THEME] = "dark"
            }
        }
    }

}