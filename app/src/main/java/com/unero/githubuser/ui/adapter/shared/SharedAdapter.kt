package com.unero.githubuser.ui.adapter.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.ItemUserBinding

// Used by Home, Following, Followers
class SharedAdapter: RecyclerView.Adapter<SharedViewHolder>() {
    private var data = listOf<User>()
    private var fragmentName: String = ""

    fun setData(newData: List<User>){
        val diffUtil = UserDiffUtil(data, newData)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        data = newData
        diffResults.dispatchUpdatesTo(this)
    }

    fun setFragment(name: String) {
        this.fragmentName = name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SharedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SharedViewHolder, position: Int) {
        holder.bind(data[position], this.fragmentName)
    }

    override fun getItemCount(): Int = data.size
}

class UserDiffUtil(
    private val oldList: List<User>,
    private val newList: List<User>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].username != newList[newItemPosition].username -> {
                false
            }
            else -> true
        }
    }
}