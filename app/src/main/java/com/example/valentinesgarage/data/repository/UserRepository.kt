package com.example.valentinesgarage.data.repository

import com.example.valentinesgarage.data.local.AppDatabase.Companion.md5
import com.example.valentinesgarage.data.local.dao.UserDao
import com.example.valentinesgarage.data.local.entity.User

class UserRepository(private val userDao: UserDao) {
    suspend fun login(username: String, password: String): User? {
        val user = userDao.getUserByUsername(username)
        return if (user != null && user.passwordHash == password.md5()) user else null
    }
}