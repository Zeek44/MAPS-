package com.example.valentinesgarage.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.valentinesgarage.data.local.AppDatabase
import com.example.valentinesgarage.data.local.entity.RepairTask
import com.example.valentinesgarage.data.repository.RepairTaskRepository
import kotlinx.coroutines.launch

class RepairTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = RepairTaskRepository(AppDatabase.getDatabase(application).repairTaskDao())

    fun getTasksForTruck(truckId: Int) = repo.getTasksForTruck(truckId)

    fun getTasksByMechanic(username: String) = repo.getTasksByMechanic(username)

    fun getAllTasks() = repo.getAllTasks()

    fun insertTask(task: RepairTask) = viewModelScope.launch { repo.insertTask(task) }

    fun updateTask(task: RepairTask) = viewModelScope.launch { repo.updateTask(task) }
}