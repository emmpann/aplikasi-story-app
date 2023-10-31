package com.github.emmpann.aplikasistoryapp.core.data.remote.response.story

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "story")
data class StoryResponse(
    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Double,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Double
)