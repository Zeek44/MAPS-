package com.garage.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A note left by a mechanic during servicing of a truck.
 * Multiple mechanics can leave notes on the same truck,
 * giving Valentine a full picture of what happened.
 */
@Entity(
    tableName = "mechanic_notes",
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
            childColumns = ["mechanicId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("truckId"),
        Index("mechanicId")
    ]
)
data class MechanicNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val truckId: Int,
    val mechanicId: Int,

    val noteContent: String,

    val timestamp: Long = System.currentTimeMillis()
)
