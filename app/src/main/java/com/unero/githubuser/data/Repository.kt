package com.unero.githubuser.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.unero.githubuser.data.api.Client
import com.unero.githubuser.data.model.Profile
import com.unero.githubuser.data.model.Result
import com.unero.githubuser.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {
    val user = MutableLiveData<Result>()
    val profile = MutableLiveData<Profile>()
    val followers = MutableLiveData<ArrayList<User>>()
    val following = MutableLiveData<ArrayList<User>>()

    fun search(username: String): MutableLiveData<Result> {
        val call = Client.api.findUserByUsername(username)

        call.enqueue(object: Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                user.value = response.body()
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.getStackTraceString(t)
            }
        })
        return user
    }

    fun profile(username: String): MutableLiveData<Profile> {
        val call = Client.api.findUserDetail(username)

        call.enqueue(object: Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                val wadah = response.body()
                profile.value = wadah
                if (wadah?.location == null) {
                    profile.value?.location = "Not Included"
                }
                if (wadah?.company == null) {
                    profile.value?.company = "Not Included"
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Log.getStackTraceString(t)
            }
        })
        return profile
    }

    fun searchFollowers(username: String): MutableLiveData<ArrayList<User>> {
        val call = Client.api.findUserFollowers(username)

        call.enqueue(object: Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                followers.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.getStackTraceString(t)
            }
        })
        return followers
    }

    fun searchFollowing(username: String): MutableLiveData<ArrayList<User>> {
        val call = Client.api.findUserFollowing(username)

        call.enqueue(object: Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                following.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.getStackTraceString(t)
            }
        })
        return following
    }
}