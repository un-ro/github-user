package com.unero.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
        binding.tvloc.text = "${user.location}"
        binding.tvrepo.text = "${user.repository} Repositories"
        binding.tvcompany.text = "${user.company}"
        binding.tvfollower.text = "Followed by ${user.followers} users"
        binding.tvfollowing.text = "Following ${user.following} users"

        // Add icon to TextView
        binding.tvloc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_place_24, 0, 0, 0)
        binding.tvcompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_work_24, 0, 0, 0)
        binding.tvfollower.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_people_alt_24, 0, 0, 0)
        binding.tvfollowing.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_people_alt_24, 0, 0, 0)
        binding.tvrepo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_folder_24, 0, 0, 0)

        setContentView(binding.root)

        // Back button at ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "${user.username} Profile"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}