package com.github.emmpann.aplikasistoryapp.core.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("loginResult")
	val loginResult: User,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class User(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("token")
	val token: String,
)
