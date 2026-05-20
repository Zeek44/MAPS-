package com.example.valentinesgarage.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.valentinesgarage.data.local.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE isApproved = 0")
    fun getPendingUsers(): LiveData<List<User>>

    @Query("UPDATE users SET isApproved = 1 WHERE username = :username")
    suspend fun approveUser(username: String)

    @Query("DELETE FROM users WHERE username = :username")
    suspend fun deleteUser(username: String)
}