package com.garage.app.data.repository

import com.garage.app.data.dao.MechanicDao
import com.garage.app.data.dao.MechanicNoteDao
import com.garage.app.data.model.Mechanic
import com.garage.app.data.model.MechanicNote
import com.garage.app.data.model.MechanicWithTasks
import kotlinx.coroutines.flow.Flow

/**
 * Repository for mechanic accounts and their notes.
 */
class MechanicRepository(
    private val mechanicDao: MechanicDao,
    private val mechanicNoteDao: MechanicNoteDao
) {

    val allMechanicsWithTasks: Flow<List<MechanicWithTasks>> =
        mechanicDao.getAllMechanicsWithTasks()

    /**
     * Basic login check — compares username + password hash.
     * Returns the Mechanic if credentials match, null otherwise.
     */
    suspend fun login(username: String, password: String): Mechanic? {
        val mechanic = mechanicDao.getMechanicByUsername(username) ?: return null
        // Replace this with proper hashing (e.g. BCrypt) in production
        return if (mechanic.passwordHash == password) mechanic else null
    }

    suspend fun getMechanicById(id: Int): Mechanic? = mechanicDao.getMechanicById(id)

    suspend fun getMechanicWithTasks(mechanicId: Int): MechanicWithTasks? =
        mechanicDao.getMechanicWithTasks(mechanicId)

    suspend fun addMechanic(
        name: String,
        username: String,
        password: String,
        role: String = "mechanic"
    ): Long {
        require(name.isNotBlank()) { "Name cannot be empty." }
        require(username.isNotBlank()) { "Username cannot be empty." }
        require(password.isNotBlank()) { "Password cannot be empty." }
        return mechanicDao.insertMechanic(
            Mechanic(name = name, username = username, passwordHash = password, role = role)
        )
    }

    // ─── Notes ─────────────────────────────────────────────────────────────────

    fun getNotesForTruck(truckId: Int): Flow<List<MechanicNote>> =
        mechanicNoteDao.getNotesForTruck(truckId)

    suspend fun addNote(truckId: Int, mechanicId: Int, noteContent: String): Long {
        require(noteContent.isNotBlank()) { "Note cannot be empty." }
        return mechanicNoteDao.insertNote(
            MechanicNote(truckId = truckId, mechanicId = mechanicId, noteContent = noteContent.trim())
        )
    }

    suspend fun deleteNote(note: MechanicNote) = mechanicNoteDao.deleteNote(note)
}
