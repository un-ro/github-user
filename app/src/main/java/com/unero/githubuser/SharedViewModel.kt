package com.unero.githubuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.local.UserDatabase
import com.unero.githubuser.data.local.UserEntity
import com.unero.githubuser.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class SharedViewModel(application: Application): AndroidViewModel(application) {

    private val users: LiveData<List<UserEntity>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        users = repository.usersLD
    }

    fun add(userEnitity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(userEnitity)
        }
    }

    fun delete(userEnitity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(userEnitity)
        }
    }

    fun search(query: String): LiveData<List<UserEntity>> {
        return repository.search(query).asLiveData()
    }
}