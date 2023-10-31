package com.github.emmpann.aplikasistoryapp.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.emmpann.aplikasistoryapp.core.data.remote.response.story.StoryResponse

@Database(
    entities = [StoryResponse::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class StoryDatabase() : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}