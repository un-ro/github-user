package com.unero.githubuser.ui.adapter.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.R
import com.unero.githubuser.data.remote.model.User

class SharedAdapter: RecyclerView.Adapter<SharedViewHolder>() {
    private val mData = mutableListOf<User>()
    private var fragmentName: String = ""

    fun setData(items: List<User>?){
        this.mData.clear()
        if (items != null) {
            this.mData.addAll(items)
        }
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
        holder.bind(mData[position], this.fragmentName)
    }

    override fun getItemCount(): Int = mData.size
}