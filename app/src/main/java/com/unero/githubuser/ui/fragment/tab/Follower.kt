package com.unero.githubuser.ui.fragment.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.R
import com.unero.githubuser.ui.adapter.FollowerAdapter
import com.unero.githubuser.ui.viewmodel.DetailViewModel
import com.unero.githubuser.databinding.FragmentFollowerBinding

class Follower() : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String): Follower {
            val fragment = Follower()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var mViewModel: DetailViewModel
    private lateinit var binding: FragmentFollowerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        mViewModel.fetchFollower(arguments?.getString(ARG_USERNAME)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follower, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.layoutManager = LinearLayoutManager(context)
        val adapter = FollowerAdapter()
        mViewModel.followersMLD.observe(viewLifecycleOwner, {
            if (it.size == 0){
                binding.noFollow.text = resources.getString(R.string.zero_follower)
                binding.noFollow.visibility = View.VISIBLE
            } else {
                adapter.setList(it)
            }
        })
        binding.rv.adapter = adapter
    }
}