package com.example.valentinesgarage.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repair_tasks")
data class RepairTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val truckId: Int,
    val taskName: String,
    val isCompleted: Boolean = false,
    val notes: String = "",
    val completedBy: String = "",      // mechanic username
    val completedAt: Long? = null
)