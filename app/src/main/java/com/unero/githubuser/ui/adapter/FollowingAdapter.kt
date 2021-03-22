package com.unero.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unero.githubuser.R
import com.unero.githubuser.data.model.User
import com.unero.githubuser.databinding.ItemFollowBinding

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    private var mList = ArrayList<User>()

    fun setList(list: ArrayList<User>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFollowBinding.bind(itemView)
        fun bind(user: User){
            binding.tvUsername.text = user.login
            Picasso.get().load(user.avatar_url).into(binding.imgAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_follow, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size
}