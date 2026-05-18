package com.example.valentinesgarage.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.valentinesgarage.data.local.entity.RepairTask

@Dao
interface RepairTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: RepairTask): Long

    @Update
    suspend fun updateTask(task: RepairTask)

    @Query("SELECT * FROM repair_tasks WHERE truckId = :truckId")
    fun getTasksForTruck(truckId: Int): LiveData<List<RepairTask>>

    @Query("SELECT * FROM repair_tasks WHERE completedBy = :username")
    fun getTasksByMechanic(username: String): LiveData<List<RepairTask>>

    @Query("SELECT * FROM repair_tasks")
    fun getAllTasks(): LiveData<List<RepairTask>>
}