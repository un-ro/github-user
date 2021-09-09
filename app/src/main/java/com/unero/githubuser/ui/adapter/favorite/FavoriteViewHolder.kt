package com.unero.githubuser.ui.adapter.favorite

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unero.githubuser.data.local.model.Favorite
import com.unero.githubuser.databinding.ItemUserBinding
import com.unero.githubuser.ui.fragments.favorite.FavoriteFragmentDirections

class FavoriteViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(favorite: Favorite) {
        binding.tvUsername.text = favorite.username
        Glide.with(itemView).load(favorite.avatar).into(binding.imgAvatar)
        binding.root.setOnClickListener {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetail(favorite.username)
            it.findNavController().navigate(action)
        }
    }
}