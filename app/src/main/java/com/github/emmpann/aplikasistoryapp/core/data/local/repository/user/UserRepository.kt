package com.github.emmpann.aplikasistoryapp.core.data.local.repository.user

import androidx.lifecycle.liveData
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.Result
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.UserPreference
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.User
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.ApiService
import retrofit2.HttpException

class UserRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    fun login(email: String, password: String) = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.login(email, password)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            emit(Result.Error(e.message().toString()))
        }
    }

    fun signUp(name: String, email: String, password: String) = liveData {
        emit(Result.Loading)

        try {
            val successResponse = apiService.register(name, email, password)
            emit(Result.Success(successResponse))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun getSession() = userPreference.getSession()

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}