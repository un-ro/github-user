package com.unero.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.R
import com.unero.githubuser.data.model.User

class FollowingAdapter: RecyclerView.Adapter<FollowViewHolder>() {
    private var mList = ArrayList<User>()

    fun setList(list: ArrayList<User>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_follow, parent, false)
        return FollowViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size
}