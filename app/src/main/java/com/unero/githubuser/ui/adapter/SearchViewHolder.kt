package com.unero.githubuser.ui.adapter

import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unero.githubuser.R
import com.unero.githubuser.data.model.User
import com.unero.githubuser.databinding.ItemResultBinding

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemResultBinding.bind(itemView)

    fun bind(user: User) {
        binding.tvUsername.text = user.login
        Picasso.get().load(user.avatar_url).into(binding.imgAvatar)
        binding.root.setOnClickListener {
            val bundle = bundleOf("id" to user.login)
            it.findNavController().navigate(R.id.action_search_to_detail, bundle)
        }
    }
}
