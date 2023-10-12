package com.github.emmpann.aplikasistoryapp.core.data.local.repository

import androidx.lifecycle.liveData
import com.github.emmpann.aplikasistoryapp.core.data.local.database.UserDao
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.UserPreference
import com.github.emmpann.aplikasistoryapp.core.data.local.repository.user.UserRepository
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.Result
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.Story
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.ApiService
import retrofit2.HttpException

class StoryRepository (private val apiService: ApiService) {
    fun getALlStory() = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.getAllStory()
            emit(Result.Success(successResponse.listStory))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    fun getStoryDetail(id: String) = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.getDetailStory(id)
            emit(Result.Success(successResponse.story))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}