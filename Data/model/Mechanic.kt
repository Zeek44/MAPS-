package com.garage.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a mechanic (employee) in the garage system.
 * Valentine uses this to track who did what on each vehicle.
 */
@Entity(tableName = "mechanics")
data class Mechanic(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    /** Username used to log in to the app */
    val username: String,

    /** Hashed password — never store plain text */
    val passwordHash: String,

    /** Role: "mechanic" or "manager" (Valentine's role) */
    val role: String = "mechanic"
)
