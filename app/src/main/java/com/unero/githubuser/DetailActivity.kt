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
        binding.tvloc.text = "Live at ${user.location}"
        binding.tvrepo.text = "Repositories: ${user.repository}"
        binding.tvcompany.text = "Work at ${user.company}"
        binding.tvfollower.text = "Followed by ${user.followers} users"
        binding.tvfollowing.text = "Following ${user.following} users"

        setContentView(binding.root)
    }
}