package com.garage.app.data.repository

import com.garage.app.data.dao.TruckDao
import com.garage.app.data.model.Truck
import com.garage.app.data.model.TruckWithDetails
import kotlinx.coroutines.flow.Flow

/**
 * Repository for truck data.
 * The ViewModels only talk to this — never directly to the DAO.
 * Keeps business logic out of the UI layer.
 */
class TruckRepository(private val truckDao: TruckDao) {

    val activeTrucks: Flow<List<Truck>> = truckDao.getActiveTrucks()

    val allTrucksWithDetails: Flow<List<TruckWithDetails>> =
        truckDao.getAllTrucksWithDetails()

    /**
     * Check a truck into the garage.
     * Validates that kilometers and condition are provided before saving.
     * Returns the new truck's ID or -1 on validation failure.
     */
    suspend fun checkInTruck(
        truckNumber: String,
        kilometersDriven: Int,
        condition: String,
        conditionNotes: String = ""
    ): Long {
        require(truckNumber.isNotBlank()) { "Truck number cannot be empty." }
        require(kilometersDriven >= 0) { "Kilometers cannot be negative." }
        require(condition in listOf("Good", "Fair", "Poor")) {
            "Condition must be Good, Fair, or Poor."
        }

        val truck = Truck(
            truckNumber = truckNumber.trim().uppercase(),
            kilometersDriven = kilometersDriven,
            condition = condition,
            conditionNotes = conditionNotes
        )
        return truckDao.insertTruck(truck)
    }

    suspend fun checkOutTruck(truckId: Int) = truckDao.checkOutTruck(truckId)

    suspend fun getTruckById(truckId: Int): Truck? = truckDao.getTruckById(truckId)

    suspend fun getTruckWithDetails(truckId: Int): TruckWithDetails? =
        truckDao.getTruckWithDetails(truckId)

    suspend fun updateTruck(truck: Truck) = truckDao.updateTruck(truck)

    suspend fun deleteTruck(truck: Truck) = truckDao.deleteTruck(truck)
}
