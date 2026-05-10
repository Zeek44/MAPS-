package com.garage.app.data.dao

import androidx.room.*
import com.garage.app.data.model.Mechanic
import com.garage.app.data.model.MechanicWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface MechanicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMechanic(mechanic: Mechanic): Long

    @Update
    suspend fun updateMechanic(mechanic: Mechanic)

    @Delete
    suspend fun deleteMechanic(mechanic: Mechanic)

    @Query("SELECT * FROM mechanics WHERE id = :id")
    suspend fun getMechanicById(id: Int): Mechanic?

    @Query("SELECT * FROM mechanics WHERE username = :username LIMIT 1")
    suspend fun getMechanicByUsername(username: String): Mechanic?

    @Query("SELECT * FROM mechanics ORDER BY name ASC")
    fun getAllMechanics(): Flow<List<Mechanic>>

    /**
     * Get a mechanic with every task they've completed.
     * Valentine uses this to see who pulled their weight.
     */
    @Transaction
    @Query("SELECT * FROM mechanics WHERE id = :mechanicId")
    suspend fun getMechanicWithTasks(mechanicId: Int): MechanicWithTasks?

    @Transaction
    @Query("SELECT * FROM mechanics ORDER BY name ASC")
    fun getAllMechanicsWithTasks(): Flow<List<MechanicWithTasks>>
}
