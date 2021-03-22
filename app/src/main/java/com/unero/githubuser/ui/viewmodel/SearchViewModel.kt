package com.unero.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.model.Result

class SearchViewModel(): ViewModel() {
    var dataMLD: MutableLiveData<Result> = MutableLiveData()

    fun search(username: String): LiveData<Result> {
        dataMLD = Repository.search(username)
        return dataMLD
    }
}