package com.unero.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.unero.githubuser.R
import com.unero.githubuser.ui.adapter.SectionsPagerAdapter
import com.unero.githubuser.data.model.Profile
import com.unero.githubuser.ui.viewmodel.DetailViewModel
import com.unero.githubuser.databinding.FragmentDetailBinding

class Detail : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: FragmentDetailBinding
    private lateinit var mViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        mViewModel.fetchProfile(arguments?.getString("id")!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tab Layout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = arguments?.getString("id")!!
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()

        // Layout Related
        mViewModel.profile?.observe(viewLifecycleOwner, {
            binding.profile = it
            drawIcon(it)
        })
    }

    private fun drawIcon(profile: Profile) {
        Picasso.get().load(profile.avatar_url).into(binding.imgAvatar)
        val repo = resources.getString(R.string.repo_value, profile.public_repos)
        binding.tvRepo.text = repo
        binding.tvRepo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.repo_icon, 0, 0, 0)
        if (profile.location != null)
            binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.map_icon, 0, 0, 0)
        if (profile.company != null)
            binding.tvCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.work_icon, 0, 0, 0)

        val tvFollower = resources.getString(R.string.dynamic_tab_1, profile.followers)
        val tvFollowing = resources.getString(R.string.dynamic_tab_2, profile.following)
        binding.tabs.getTabAt(0)?.text = tvFollower
        binding.tabs.getTabAt(1)?.text = tvFollowing
    }
}