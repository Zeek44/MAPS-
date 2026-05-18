package com.garage.app.data.repository

import com.garage.app.data.dao.RepairTaskDao
import com.garage.app.data.model.RepairTask
import kotlinx.coroutines.flow.Flow

/**
 * Repository for repair tasks.
 * Mechanics interact with tasks through here.
 */
class RepairTaskRepository(private val repairTaskDao: RepairTaskDao) {

    fun getTasksForTruck(truckId: Int): Flow<List<RepairTask>> =
        repairTaskDao.getTasksForTruck(truckId)

    fun getPendingTasksForTruck(truckId: Int): Flow<List<RepairTask>> =
        repairTaskDao.getPendingTasksForTruck(truckId)

    fun getCompletedTasksByMechanic(mechanicId: Int): Flow<List<RepairTask>> =
        repairTaskDao.getCompletedTasksByMechanic(mechanicId)

    suspend fun addTask(truckId: Int, description: String): Long {
        require(description.isNotBlank()) { "Task description cannot be empty." }
        return repairTaskDao.insertTask(
            RepairTask(truckId = truckId, taskDescription = description.trim())
        )
    }

    suspend fun addDefaultTasks(truckId: Int) {
        val defaultTasks = listOf(
            "Inspect brakes",
            "Check oil level and change if needed",
            "Inspect tires and pressure",
            "Check all lights",
            "Inspect engine coolant",
            "Check gearbox and transmission fluid",
            "Inspect exhaust system",
            "Check battery",
            "Inspect steering and suspension",
            "Road test after service"
        ).map { RepairTask(truckId = truckId, taskDescription = it) }

        repairTaskDao.insertTasks(defaultTasks)
    }

    /**
     * Mechanic ticks off a task.
     * Notes are optional but encouraged — Valentine is watching.
     */
    suspend fun completeTask(
        taskId: Int,
        mechanicId: Int,
        notes: String = ""
    ) = repairTaskDao.completeTask(taskId, mechanicId, notes)

    suspend fun deleteTask(task: RepairTask) = repairTaskDao.deleteTask(task)
}
