package com.github.emmpann.aplikasistoryapp.core.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class RequestRegisterResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
