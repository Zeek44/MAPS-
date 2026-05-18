package com.garage.app.data.dao

import androidx.room.*
import com.garage.app.data.model.Truck
import com.garage.app.data.model.TruckWithDetails
import kotlinx.coroutines.flow.Flow

/**
 * DAO for all truck-related database operations.
 */
@Dao
interface TruckDao {

    // ─── INSERT ────────────────────────────────────────────────────────────────

    /**
     * Check a new truck into the garage.
     * Returns the auto-generated row ID.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTruck(truck: Truck): Long

    // ─── UPDATE ────────────────────────────────────────────────────────────────

    @Update
    suspend fun updateTruck(truck: Truck)

    /**
     * Mark a truck as checked out once all repairs are done.
     */
    @Query("UPDATE trucks SET isCheckedOut = 1 WHERE id = :truckId")
    suspend fun checkOutTruck(truckId: Int)

    // ─── DELETE ────────────────────────────────────────────────────────────────

    @Delete
    suspend fun deleteTruck(truck: Truck)

    // ─── QUERIES ───────────────────────────────────────────────────────────────

    /** All trucks currently in the garage (not checked out). */
    @Query("SELECT * FROM trucks WHERE isCheckedOut = 0 ORDER BY checkInTimestamp DESC")
    fun getActiveTrucks(): Flow<List<Truck>>

    /** Every truck ever — for reports. */
    @Query("SELECT * FROM trucks ORDER BY checkInTimestamp DESC")
    fun getAllTrucks(): Flow<List<Truck>>

    @Query("SELECT * FROM trucks WHERE id = :truckId")
    suspend fun getTruckById(truckId: Int): Truck?

    @Query("SELECT * FROM trucks WHERE truckNumber = :truckNumber LIMIT 1")
    suspend fun getTruckByNumber(truckNumber: String): Truck?

    /**
     * Full truck report: truck + all its tasks + all its notes.
     * This is what Valentine sees on the reports screen.
     */
    @Transaction
    @Query("SELECT * FROM trucks WHERE id = :truckId")
    suspend fun getTruckWithDetails(truckId: Int): TruckWithDetails?

    @Transaction
    @Query("SELECT * FROM trucks ORDER BY checkInTimestamp DESC")
    fun getAllTrucksWithDetails(): Flow<List<TruckWithDetails>>
}
