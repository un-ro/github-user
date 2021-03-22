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
import com.unero.githubuser.ui.adapter.FollowingAdapter
import com.unero.githubuser.ui.viewmodel.DetailViewModel
import com.unero.githubuser.databinding.FragmentFollowingBinding

class Following() : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String): Following {
            val fragment = Following()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var mViewModel: DetailViewModel
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        mViewModel.fetchFollowing(arguments?.getString(ARG_USERNAME)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.layoutManager = LinearLayoutManager(context)
        val adapter = FollowingAdapter()
        mViewModel.followingMLD.observe(viewLifecycleOwner, {
            if (it.size == 0) {
                binding.noFollow.text = resources.getString(R.string.zero_following)
                binding.noFollow.visibility = View.VISIBLE
            } else {
                adapter.setList(it)
            }
        })
        binding.rv.adapter = adapter
    }
}