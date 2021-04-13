package com.unero.githubuser.ui.fragments.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.local.Favorite
import com.unero.githubuser.data.local.FavoriteDatabase
import com.unero.githubuser.data.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    val listFav: LiveData<List<Favorite>>
    private val repository: LocalRepository

    init {
        val favDao = FavoriteDatabase.getDatabase(application).dao()
        repository = LocalRepository(favDao)
        listFav = repository.data
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}