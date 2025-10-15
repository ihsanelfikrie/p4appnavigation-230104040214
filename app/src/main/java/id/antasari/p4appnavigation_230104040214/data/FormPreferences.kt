package id.antasari.p4appnavigation_230104040214.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "form_prefs")

class FormPreferences(private val context: Context) {

    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val NIM_KEY = stringPreferencesKey("nim")
    }

    val nameFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[NAME_KEY] ?: ""
    }

    val nimFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[NIM_KEY] ?: ""
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    suspend fun saveNim(nim: String) {
        context.dataStore.edit { preferences ->
            preferences[NIM_KEY] = nim
        }
    }

    suspend fun saveFormData(name: String, nim: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[NIM_KEY] = nim
        }
    }

    suspend fun clearFormData() {
        context.dataStore.edit { preferences ->
            preferences.remove(NAME_KEY)
            preferences.remove(NIM_KEY)
        }
    }
}