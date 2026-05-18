package com.example.valentinesgarage.data.repository

import com.example.valentinesgarage.data.local.dao.RepairTaskDao
import com.example.valentinesgarage.data.local.entity.RepairTask

class RepairTaskRepository(private val repairTaskDao: RepairTaskDao) {
    fun getTasksForTruck(truckId: Int) = repairTaskDao.getTasksForTruck(truckId)

    fun getTasksByMechanic(username: String) = repairTaskDao.getTasksByMechanic(username)

    fun getAllTasks() = repairTaskDao.getAllTasks()

    suspend fun insertTask(task: RepairTask): Long = repairTaskDao.insertTask(task)

    suspend fun updateTask(task: RepairTask) = repairTaskDao.updateTask(task)
}