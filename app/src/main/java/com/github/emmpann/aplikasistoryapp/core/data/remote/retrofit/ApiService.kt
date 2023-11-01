package com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit

import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.RequestAllStoryResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.RequestDetailStoryResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.RequestRegisterResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.RequestLoginResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.RequestUploadStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RequestRegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): RequestLoginResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float,
        @Part("lon") lon: Float
    ): RequestUploadStoryResponse

    @GET("stories")
    suspend fun getAllStory(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): RequestAllStoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: String,
    ): RequestDetailStoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ): RequestAllStoryResponse
}