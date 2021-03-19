package com.unero.githubuser.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.unero.githubuser.DetailActivity
import com.unero.githubuser.R
import com.unero.githubuser.data.User
import com.unero.githubuser.databinding.ItemUserBinding

class UserAdapter internal constructor(private val listUser: ArrayList<User>, private val context: Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(private val itemBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: User, context: Context) {
            itemBinding.imgAvatar.setImageResource(user.avatar)
            itemBinding.tvName.text = user.name
            itemBinding.tvUsername.text = user.username

            // Icon
            itemBinding.btnLike.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon, 0, 0, 0)
            itemBinding.btnDetail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.detail_icon, 0, 0, 0)

            itemBinding.btnLike.setOnClickListener {
                Toast.makeText(context, "You ❤️ ${user.name} Profile", Toast.LENGTH_SHORT).show()
            }

            itemBinding.btnDetail.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.USER, user)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user, this.context)
    }

    override fun getItemCount(): Int = listUser.size
}