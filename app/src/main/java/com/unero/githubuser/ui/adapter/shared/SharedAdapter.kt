package com.unero.githubuser.ui.adapter.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.R
import com.unero.githubuser.data.remote.model.User

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
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return SharedViewHolder(mView)
    }

    override fun onBindViewHolder(holder: SharedViewHolder, position: Int) {
        holder.bind(data[position], this.fragmentName)
    }

    override fun getItemCount(): Int = data.size
}