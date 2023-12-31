package com.github.emmpann.aplikasistoryapp.core.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference (private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserResponse) {
        dataStore.edit { preferences ->
            preferences[NAME] = user.name
            preferences[USER_ID] = user.userId
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserResponse> {
        return dataStore.data.map { preferences ->
            UserResponse(
                name = preferences[NAME] ?: "",
                userId = preferences[USER_ID] ?: "",
                token = preferences[TOKEN_KEY] ?: "",
                isLogin = preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val NAME = stringPreferencesKey("name")
        private val USER_ID = stringPreferencesKey("userid")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

    }
}