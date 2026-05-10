package com.garage.app.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Convenience class that bundles a truck together with
 * all its repair tasks and mechanic notes.
 * Used for the reports screen so Valentine can see everything at once.
 */
data class TruckWithDetails(
    @Embedded val truck: Truck,

    @Relation(
        parentColumn = "id",
        entityColumn = "truckId"
    )
    val repairTasks: List<RepairTask>,

    @Relation(
        parentColumn = "id",
        entityColumn = "truckId"
    )
    val mechanicNotes: List<MechanicNote>
)

/**
 * Pairs a mechanic with all the tasks they completed.
 * Directly feeds Valentine's "what did each employee do" report.
 */
data class MechanicWithTasks(
    @Embedded val mechanic: Mechanic,

    @Relation(
        parentColumn = "id",
        entityColumn = "assignedMechanicId"
    )
    val completedTasks: List<RepairTask>
)
