package com.github.emmpann.aplikasistoryapp.core.data.di

import android.app.Application
import android.util.Log
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.UserPreference
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.dataStore
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.story.StoryRepository
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.ApiService
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserPreferences(app: Application): UserPreference {
        Log.d("provideUserPreferences", "called")
        return UserPreference(app.dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(token: UserPreference): AuthInterceptor = AuthInterceptor(token)

    @Provides
    @Singleton
    fun provideApiService(authInterceptor: AuthInterceptor): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userPreferences: UserPreference, apiService: ApiService): UserRepository{
        Log.d("provideUserRepository", "called")
        return UserRepository(userPreferences, apiService)
    }

    @Provides
    @Singleton
    fun provideStoryRepository(apiService: ApiService): StoryRepository {
        Log.d("provideStoryRepository", "called")
        return StoryRepository(apiService)
    }
}