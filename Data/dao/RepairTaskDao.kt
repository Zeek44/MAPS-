package com.garage.app.data.dao

import androidx.room.*
import com.garage.app.data.model.RepairTask
import kotlinx.coroutines.flow.Flow

@Dao
interface RepairTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: RepairTask): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<RepairTask>)

    @Update
    suspend fun updateTask(task: RepairTask)

    /**
     * Mechanic ticks off a task.
     * Records who did it and when.
     */
    @Query("""
        UPDATE repair_tasks 
        SET isCompleted = 1, 
            assignedMechanicId = :mechanicId, 
            completedTimestamp = :timestamp,
            mechanicNotes = :notes
        WHERE id = :taskId
    """)
    suspend fun completeTask(
        taskId: Int,
        mechanicId: Int,
        notes: String = "",
        timestamp: Long = System.currentTimeMillis()
    )

    @Delete
    suspend fun deleteTask(task: RepairTask)

    @Query("SELECT * FROM repair_tasks WHERE truckId = :truckId ORDER BY createdTimestamp ASC")
    fun getTasksForTruck(truckId: Int): Flow<List<RepairTask>>

    @Query("SELECT * FROM repair_tasks WHERE truckId = :truckId AND isCompleted = 0")
    fun getPendingTasksForTruck(truckId: Int): Flow<List<RepairTask>>

    @Query("SELECT * FROM repair_tasks WHERE assignedMechanicId = :mechanicId AND isCompleted = 1")
    fun getCompletedTasksByMechanic(mechanicId: Int): Flow<List<RepairTask>>

    @Query("SELECT * FROM repair_tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): RepairTask?
}
