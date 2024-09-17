package eu.petrfaruzel.scratcher.features.shared.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import eu.petrfaruzel.scratcher.core.data.BaseDS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "card")

class CardDS(
    context: Context
) : BaseDS(context.dataStore) {

    companion object {
        private val SCRATCH_KEY = stringPreferencesKey("scratch_key")
        private val IS_ACTIVATED = booleanPreferencesKey("is_activated")
    }

    val scratchKey: Flow<String?> = dataStore.data.map { prefs -> prefs[SCRATCH_KEY] }
    suspend fun setScratchKey(value: String?) = replaceOrNull(SCRATCH_KEY, value)

    val isActivated: Flow<Boolean> = dataStore.data.map { prefs -> prefs[IS_ACTIVATED] == true }
    suspend fun setIsActivated(value: Boolean?) = replaceOrNull(IS_ACTIVATED, value)

}