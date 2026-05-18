package com.example.valentinesgarage.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trucks")
data class Truck(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val registrationNumber: String,
    val ownerName: String,
    val conditionDescription: String,
    val conditionRating: Int,          // 1–5
    val kilometersDriven: Double,
    val checkInTime: Long = System.currentTimeMillis(),
    val checkedInBy: String            // receptionist username
)