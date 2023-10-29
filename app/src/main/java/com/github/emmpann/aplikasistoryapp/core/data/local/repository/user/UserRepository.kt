package com.github.emmpann.aplikasistoryapp.core.data.local.repository.user

import androidx.lifecycle.liveData
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.core.data.local.pref.UserPreference
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.RequestLoginResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.UserResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException

class UserRepository(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    fun login(email: String, password: String) = flow {
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RequestLoginResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun signUp(name: String, email: String, password: String) = liveData {
        emit(ResultApi.Loading)

        try {
            val successResponse = apiService.register(name, email, password)
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    suspend fun saveSession(user: UserResponse) {
        userPreference.saveSession(user)
    }

    fun getSession() = userPreference.getSession()

    suspend fun logout() {
        userPreference.logout()
    }
}