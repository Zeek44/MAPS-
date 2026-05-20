package com.example.valentinesgarage.data.repository

import com.example.valentinesgarage.data.local.AppDatabase.Companion.md5
import com.example.valentinesgarage.data.local.dao.UserDao
import com.example.valentinesgarage.data.local.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun login(username: String, password: String): User? {
        val user = userDao.getUserByUsername(username)
        return if (user != null && user.passwordHash == password.md5() && user.isApproved) {
            user
        } else null
    }

    suspend fun register(username: String, password: String, role: String, fullName: String) {
        val user = User(
            username = username,
            passwordHash = password.md5(),
            role = role,
            fullName = fullName,
            isApproved = false
        )
        userDao.insertUser(user)
    }

    fun getPendingUsers() = userDao.getPendingUsers()

    suspend fun approveUser(username: String) = userDao.approveUser(username)

    suspend fun deleteUser(username: String) = userDao.deleteUser(username)
}