package com.unero.githubuser.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.remote.model.Result
import com.unero.ungithub.data.utils.APIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {
    // Search Response
    private var _listUser: MutableLiveData<Result> = MutableLiveData()
    val listUser: LiveData<Result> get() = _listUser

    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun findUser(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repository.findUserByUsername(username)) {
                is APIResponse.Error -> errorMessage.postValue(response.exception.message)
                is APIResponse.Success -> _listUser.postValue(response.data)
            }
        }
    }
}