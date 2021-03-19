package com.unero.githubuser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        binding.tvloc.text = user.location
        binding.tvrepo.text = "${user.repository} Repositories"
        binding.tvcompany.text = user.company
        binding.tvfollower.text = "Followed by ${user.followers} users"
        binding.tvfollowing.text = "Following ${user.following} users"

        // Add icon to TextView
        binding.tvloc.setCompoundDrawablesWithIntrinsicBounds(R.drawable.map_icon, 0, 0, 0)
        binding.tvcompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.work_icon, 0, 0, 0)
        binding.tvfollower.setCompoundDrawablesWithIntrinsicBounds(R.drawable.people_icon, 0, 0, 0)
        binding.tvfollowing.setCompoundDrawablesWithIntrinsicBounds(R.drawable.people_icon, 0, 0, 0)
        binding.tvrepo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.repo_icon, 0, 0, 0)

        // Add Icon to Button
        binding.btnShare.setCompoundDrawablesWithIntrinsicBounds(R.drawable.share_icon, 0, 0, 0)

        // Share Text Implicit Intent
        val pharagraph = """
            Hey, do you know if ${user.name} or ${user.username} on github is have ${user.followers} 
            followers and following ${user.following} users.
        """.trimIndent()
        binding.btnShare.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, pharagraph)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }

        if (user.name == "Unero")
            binding.easter.visibility = View.VISIBLE

        binding.easter.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = (Uri.parse("https://github.com/un-ro"))
            startActivity(intent)
        }

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