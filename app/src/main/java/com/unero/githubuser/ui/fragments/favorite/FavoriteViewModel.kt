package com.unero.githubuser.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.local.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository): ViewModel() {

    val listFav: LiveData<List<Favorite>> = repository.listFav()

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}