package com.unero.githubuser.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unero.githubuser.ui.fragment.tab.FollowerFragment
import com.unero.githubuser.ui.fragment.tab.FollowingFragment

class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    var username: String? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.newInstance(username ?: "")
            1 -> fragment = FollowingFragment.newInstance(username ?: "")
        }
        return fragment as Fragment
    }
}