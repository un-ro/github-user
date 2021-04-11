package com.unero.githubuser.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unero.githubuser.ui.fragments.detail.FollowerFragment
import com.unero.githubuser.ui.fragments.detail.FollowingFragment
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}