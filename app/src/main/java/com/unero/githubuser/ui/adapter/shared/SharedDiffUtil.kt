package com.unero.githubuser.ui.adapter.shared

import androidx.recyclerview.widget.DiffUtil
import com.unero.githubuser.data.remote.model.User

class SharedDiffUtil(
    private val oldList: List<User>,
    private val newList: List<User>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login == newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].login != newList[newItemPosition].login -> {
                false
            }
            oldList[oldItemPosition].avatar_url != newList[newItemPosition].avatar_url -> {
                false
            }
            else -> true
        }
    }
}