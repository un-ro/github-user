package com.unero.consumer.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unero.consumer.data.model.Favorite
import com.unero.consumer.databinding.ItemBinding

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemBinding.bind(itemView)

    fun bind(favorite: Favorite) {
        Glide.with(itemView).load(favorite.avatar).into(binding.ivAvatar)
        binding.favorite = favorite
    }
}
