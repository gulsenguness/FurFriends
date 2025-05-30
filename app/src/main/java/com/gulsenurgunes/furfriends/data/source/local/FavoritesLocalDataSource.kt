package com.gulsenurgunes.furfriends.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.datastore.preferences.core.Preferences          // ✅ DOĞRU import
import androidx.datastore.preferences.core.edit               // ✅ edit uzantısı

class FavoritesLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object { private val IDS = stringSetPreferencesKey("favorite_ids") }

    val idsFlow: Flow<Set<Int>> = dataStore.data
        .map { it[IDS]?.map(String::toInt)?.toSet() ?: emptySet() }

    suspend fun add(id: Int) = dataStore.edit { prefs ->
        prefs[IDS] = (prefs[IDS] ?: emptySet()) + id.toString()
    }

    suspend fun remove(id: Int) = dataStore.edit { prefs ->
        prefs[IDS] = (prefs[IDS] ?: emptySet()) - id.toString()
    }

    suspend fun clear() = dataStore.edit { it.clear() }

    suspend fun getIds(): Set<Int> = idsFlow.first()
}