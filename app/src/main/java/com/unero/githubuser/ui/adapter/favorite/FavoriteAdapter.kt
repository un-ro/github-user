package com.unero.githubuser.ui.adapter.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.data.local.Favorite
import com.unero.githubuser.databinding.ItemUserBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteViewHolder>() {
    private var data = listOf<Favorite>()

    fun setData(newData: List<Favorite>) {
        val diffUtil = FavDiffUtil(data, newData)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        data = newData
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

}

class FavDiffUtil(
    private val oldList: List<Favorite>,
    private val newList: List<Favorite>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].username != newList[newItemPosition].username -> {
                false
            }
            else -> true
        }
    }
}