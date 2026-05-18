package com.example.valentinesgarage.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.valentinesgarage.data.local.AppDatabase
import com.example.valentinesgarage.data.local.entity.Truck
import com.example.valentinesgarage.data.repository.TruckRepository
import kotlinx.coroutines.launch

class TruckViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = TruckRepository(AppDatabase.getDatabase(application).truckDao())

    val allTrucks: LiveData<List<Truck>> = repo.allTrucks

    fun insertTruck(truck: Truck) = viewModelScope.launch {
        repo.insertTruck(truck)
    }

    fun getTrucksByEmployee(username: String) = repo.getTrucksByEmployee(username)
}