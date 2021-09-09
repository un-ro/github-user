package com.unero.githubuser.ui.adapter.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.ItemUserBinding

// Used by Home, Following, Followers
class SharedAdapter: RecyclerView.Adapter<SharedViewHolder>() {
    private var data = mutableListOf<User>()
    private var fragmentName: String = ""

    fun setData(newData: List<User>?){
        this.data.clear()
        newData?.let { this.data.addAll(it) }
        notifyDataSetChanged()
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