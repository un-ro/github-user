package com.unero.githubuser

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.adapter.UserAdapter
import com.unero.githubuser.data.User
import com.unero.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Data Collection
    private var users = arrayListOf<User>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setup()
        binding.rv.adapter = UserAdapter(users, this)
        binding.rv.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)
    }

    private fun setup() {
        val names: Array<String> = resources.getStringArray(R.array.name)
        val username: Array<String> = resources.getStringArray(R.array.username)
        val avatar: TypedArray = resources.obtainTypedArray(R.array.avatar)
        val location: Array<String> = resources.getStringArray(R.array.location)
        val repos: Array<String> = resources.getStringArray(R.array.repository)
        val company: Array<String> = resources.getStringArray(R.array.company)
        val followers: Array<String> = resources.getStringArray(R.array.followers)
        val following: Array<String> = resources.getStringArray(R.array.following)

        // Fill
        for (pos in names.indices) {
            val user = User(
                avatar.getResourceId(pos, -1),
                names[pos],
                username[pos],
                location[pos],
                repos[pos],
                company[pos],
                followers[pos],
                following[pos]
            )
            users.add(user)
        }

        // TypedArray should recycled
        avatar.recycle()
    }
}