package br.com.fiap.runmail.telas.preferencias


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class PreferencesManager(private val context: Context) {
    private val dataStore = context.dataStore

    val themePreference: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.THEME] ?: false
    }

    val userName: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_NAME] ?: ""
    }

    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: true
    }

    val vibrationEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.VIBRATION_ENABLED] ?: true
    }

    val autoSignature: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.AUTO_SIGNATURE] ?: false
    }

    val signatureText: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SIGNATURE_TEXT] ?: ""
    }

    suspend fun savePreferences(
        theme: Boolean,
        name: String,
        notificationsEnabled: Boolean,
        vibrationEnabled: Boolean,
        autoSignature: Boolean,
        signatureText: String
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = theme
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = notificationsEnabled
            preferences[PreferencesKeys.VIBRATION_ENABLED] = vibrationEnabled
            preferences[PreferencesKeys.AUTO_SIGNATURE] = autoSignature
            preferences[PreferencesKeys.SIGNATURE_TEXT] = signatureText
        }
    }

    private object PreferencesKeys {
        val THEME = booleanPreferencesKey("theme")
        val USER_NAME = stringPreferencesKey("user_name")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val AUTO_SIGNATURE = booleanPreferencesKey("auto_signature")
        val SIGNATURE_TEXT = stringPreferencesKey("signature_text")
    }
}








