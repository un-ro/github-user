package com.unero.githubuser

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.adapter.UserAdapter
import com.unero.githubuser.data.User
import com.unero.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Data
    private lateinit var dataName: Array<String>
    private lateinit var dataUsername: Array<String>
    private lateinit var dataAvatar: TypedArray
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
        dataName = resources.getStringArray(R.array.name)
        dataUsername = resources.getStringArray(R.array.username)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)

        // Fill
        for (pos in dataName.indices) {
            val user = User(
                dataAvatar.getResourceId(pos, -1),
                dataName[pos],
                dataUsername[pos]
            )
            users.add(user)
        }
    }
}