package com.unero.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unero.githubuser.data.User
import com.unero.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        val user = intent.getParcelableExtra<User>(USER) as User

        binding.imgAvatar.setImageResource(user.avatar)
        binding.tvName.text = user.name
        binding.tvUsername.text = user.username

        setContentView(binding.root)
    }
}