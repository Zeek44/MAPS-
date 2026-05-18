package com.example.valentinesgarage.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.valentinesgarage.data.local.AppDatabase
import com.example.valentinesgarage.data.local.entity.User
import com.example.valentinesgarage.data.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun login(username: String, password: String) = viewModelScope.launch {
        _currentUser.postValue(repo.login(username, password))
    }

    fun logout() { _currentUser.value = null }
}