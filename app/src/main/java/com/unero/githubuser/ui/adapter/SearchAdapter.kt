package com.unero.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.R
import com.unero.githubuser.data.model.User

class SearchAdapter: RecyclerView.Adapter<SearchViewHolder>() {
    private val mData = ArrayList<User>()

    fun setData(items: ArrayList<User>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return SearchViewHolder(mView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}