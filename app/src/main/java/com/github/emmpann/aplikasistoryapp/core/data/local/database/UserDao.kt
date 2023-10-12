package com.github.emmpann.aplikasistoryapp.core.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserModel)

    @Query("SELECT * FROM UserModel WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserModel
}