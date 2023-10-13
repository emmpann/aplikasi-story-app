package com.github.emmpann.aplikasistoryapp.core.data.remote.response.user

import com.google.gson.annotations.SerializedName
data class User(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String,

    val isLogin: Boolean
)