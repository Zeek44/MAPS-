package com.garage.app.data.dao

import androidx.room.*
import com.garage.app.data.model.MechanicNote
import kotlinx.coroutines.flow.Flow

@Dao
interface MechanicNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: MechanicNote): Long

    @Update
    suspend fun updateNote(note: MechanicNote)

    @Delete
    suspend fun deleteNote(note: MechanicNote)

    @Query("SELECT * FROM mechanic_notes WHERE truckId = :truckId ORDER BY timestamp DESC")
    fun getNotesForTruck(truckId: Int): Flow<List<MechanicNote>>

    @Query("SELECT * FROM mechanic_notes WHERE mechanicId = :mechanicId ORDER BY timestamp DESC")
    fun getNotesByMechanic(mechanicId: Int): Flow<List<MechanicNote>>
}
