package com.github.emmpann.aplikasistoryapp.core.data.remote.response.story

import com.google.gson.annotations.SerializedName

data class RequestUploadStoryResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
