package com.github.emmpann.aplikasistoryapp.core.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<StoryResponse>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, StoryResponse>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}