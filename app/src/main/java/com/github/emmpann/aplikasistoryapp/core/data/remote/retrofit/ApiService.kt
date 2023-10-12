package com.github.emmpann.aplikasistoryapp.core.data.remote.retrofit

import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.AllStoryResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.DetailStoryResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.RegisterResponse
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.user.Response
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.UploadStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): UploadStoryResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response

    @GET("stories")
    suspend fun getAllStory(
    ): AllStoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
            @Path("id") id: String
    ): DetailStoryResponse
}