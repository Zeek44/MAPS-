 package com.garage.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A single repair or service task assigned to a truck.
 * Mechanics tick these off and leave notes so nothing
 * falls through the cracks between shifts.
 */
@Entity(
    tableName = "repair_tasks",
    foreignKeys = [
        ForeignKey(
            entity = Truck::class,
            parentColumns = ["id"],
            childColumns = ["truckId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Mechanic::class,
            parentColumns = ["id"],
            childColumns = ["assignedMechanicId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("truckId"),
        Index("assignedMechanicId")
    ]
)
data class RepairTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /** The truck this task belongs to */
    val truckId: Int,

    /** Short description of the task, e.g. "Replace brake pads" */
    val taskDescription: String,

    /** Whether this task has been completed */
    val isCompleted: Boolean = false,

    /**
     * Mechanic who completed/last touched this task.
     * Nullable — task may not be assigned yet.
     */
    val assignedMechanicId: Int? = null,

    /** Notes the mechanic left about this specific task */
    val mechanicNotes: String = "",

    /** When the task was marked complete */
    val completedTimestamp: Long? = null,

    /** When the task was created */
    val createdTimestamp: Long = System.currentTimeMillis()
)
