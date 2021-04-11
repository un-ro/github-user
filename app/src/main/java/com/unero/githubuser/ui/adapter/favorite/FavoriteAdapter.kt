package com.unero.githubuser.ui.adapter.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.R
import com.unero.githubuser.data.local.Favorite

class FavoriteAdapter: RecyclerView.Adapter<FavoriteViewHolder>() {
    private val data = mutableListOf<Favorite>()

    fun setData(newData: List<Favorite>) {
        this.data.clear()
        this.data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_main, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}