package com.unero.githubuser.ui.adapter.shared

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unero.githubuser.R
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.ItemUserBinding

class SharedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemUserBinding.bind(itemView)

    fun bind(user: User, name: String) {
        binding.tvUsername.text = user.login
        Glide.with(itemView).load(user.avatar_url).into(binding.imgAvatar)
        binding.root.setOnClickListener {
            val bundle = bundleOf("username" to user.login)
            if (name == "HomeFragment") {
                it.findNavController().navigate(R.id.action_homeFragment_to_detail, bundle)
            } else {
                it.findNavController().navigate(R.id.action_detail_self, bundle)
            }
        }
    }
}
