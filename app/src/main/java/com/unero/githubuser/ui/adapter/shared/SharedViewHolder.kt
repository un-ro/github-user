package com.unero.githubuser.ui.adapter.shared

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.ItemUserBinding
import com.unero.githubuser.ui.fragments.detail.DetailFragmentDirections
import com.unero.githubuser.ui.fragments.home.HomeFragmentDirections

class SharedViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User, name: String) {
        binding.tvUsername.text = user.username
        Glide.with(itemView).load(user.avatar).into(binding.imgAvatar)
        binding.root.setOnClickListener {
            if (name == "HomeFragment") {
                val action = HomeFragmentDirections.actionHomeFragmentToDetail(user.username)
                it.findNavController().navigate(action)
            } else {
                val action = DetailFragmentDirections.actionDetailSelf(user.username)
                it.findNavController().navigate(action)
            }
        }
    }
}
