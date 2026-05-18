package com.example.valentinesgarage.data.repository

import androidx.lifecycle.LiveData
import com.example.valentinesgarage.data.local.dao.TruckDao
import com.example.valentinesgarage.data.local.entity.Truck

class TruckRepository(private val truckDao: TruckDao) {
    val allTrucks: LiveData<List<Truck>> = truckDao.getAllTrucks()

    suspend fun insertTruck(truck: Truck): Long = truckDao.insertTruck(truck)

    suspend fun getTruckById(id: Int): Truck? = truckDao.getTruckById(id)

    fun getTrucksByEmployee(username: String) = truckDao.getTrucksByEmployee(username)
}