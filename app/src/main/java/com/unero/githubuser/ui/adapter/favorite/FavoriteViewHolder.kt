package com.unero.githubuser.ui.adapter.favorite

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unero.githubuser.R
import com.unero.githubuser.data.local.Favorite
import com.unero.githubuser.databinding.ItemFavoriteMainBinding

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemFavoriteMainBinding.bind(itemView)

    fun bind(favorite: Favorite) {
        binding.favorite = favorite
        Glide.with(itemView).load(favorite.avatar).into(binding.ivAvatar)

        binding.cvItem.setOnClickListener {
            val bundle = bundleOf("username" to favorite.username)
            it.findNavController().navigate(R.id.action_favoriteFragment_to_detail, bundle)
        }
    }
}