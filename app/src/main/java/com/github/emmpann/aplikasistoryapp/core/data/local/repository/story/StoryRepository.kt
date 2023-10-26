package com.github.emmpann.aplikasistoryapp.core.data.local.repository.story

import androidx.lifecycle.liveData
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.ResultApi
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.RequestAllStoryResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.RequestUploadStoryResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class StoryRepository (private val apiService: ApiService) {
    fun getAllStory() = flow {
        try {
            val successResponse = apiService.getAllStory()
            emit(ResultApi.Success(successResponse.listStory))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RequestAllStoryResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun getStoryDetail(id: String) = flow {
        try {
            val successResponse = apiService.getDetailStory(id)
            emit(ResultApi.Success(successResponse.story))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }.onStart {
        emit(ResultApi.Loading)
    }.flowOn(Dispatchers.IO)

    fun uploadStory(imageFile: File, description: String) = liveData {
        emit(ResultApi.Loading)

        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody()

        val multiPartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )

        try {
            val successResponse = apiService.uploadStory(multiPartBody, requestBody)
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RequestUploadStoryResponse::class.java)
            emit(ResultApi.Error(errorResponse.message))
        }
    }
}