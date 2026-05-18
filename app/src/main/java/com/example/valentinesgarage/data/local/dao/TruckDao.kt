package com.example.valentinesgarage.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.valentinesgarage.data.local.entity.Truck

@Dao
interface TruckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTruck(truck: Truck): Long

    @Query("SELECT * FROM trucks ORDER BY checkInTime DESC")
    fun getAllTrucks(): LiveData<List<Truck>>

    @Query("SELECT * FROM trucks WHERE id = :truckId")
    suspend fun getTruckById(truckId: Int): Truck?

    @Query("SELECT * FROM trucks WHERE checkedInBy = :username")
    fun getTrucksByEmployee(username: String): LiveData<List<Truck>>
}