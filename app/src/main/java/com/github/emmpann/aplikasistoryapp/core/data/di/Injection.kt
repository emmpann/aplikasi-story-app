package com.github.emmpann.aplikasistoryapp.core.data.di

import android.content.Context
import com.github.emmpann.aplikasistoryapp.core.data.local.database.UserRoomDatabase
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.UserPreference
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.dataStore
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.StoryRepository
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val database = UserRoomDatabase.getDatabase(context)
        val userDao = database.userDao()
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(userDao, pref, apiService)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token ?: "")
        return StoryRepository.getInstance(apiService)
    }
}