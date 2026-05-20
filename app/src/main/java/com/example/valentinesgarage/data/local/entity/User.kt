package com.example.valentinesgarage.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val passwordHash: String,
    val role: String,
    val isApproved: Boolean = false,
    val fullName: String = ""
)