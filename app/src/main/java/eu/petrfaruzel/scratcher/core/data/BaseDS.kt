package eu.petrfaruzel.scratcher.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

open class BaseDS(protected val dataStore: DataStore<Preferences>) {
    suspend fun <T> replaceOrNull(key: Preferences.Key<T>, value: T?) {
        dataStore.edit { prefs ->
            if (value != null) {
                prefs[key] = value
            } else {
                prefs.remove(key)
            }
        }
    }
}