package com.unero.githubuser.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unero.githubuser.data.model.User
import com.unero.githubuser.databinding.ItemFollowBinding

class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemFollowBinding.bind(itemView)
    fun bind(user: User){
        binding.tvUsername.text = user.login
        Picasso.get().load(user.avatar_url).into(binding.imgAvatar)
    }
}