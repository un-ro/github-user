package com.unero.githubuser.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.R
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.FragmentFollowerBinding
import com.unero.githubuser.ui.adapter.shared.SharedAdapter
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FollowerFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var adapter: SharedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follower, container, false)
        binding.lifecycleOwner = this

        setupRV()

        binding.pb.visibility = View.VISIBLE
        binding.rv.visibility = View.INVISIBLE
        binding.noFollow.visibility = View.INVISIBLE

        viewModel.listFollower.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                val follower = it.body()
                if (follower != null) {
                    render(true, follower)
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            render(false, null)
        })

        return binding.root
    }

    private fun setupRV() {
        adapter = SharedAdapter()
        adapter.setFragment(this::class.java.simpleName)
        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun render(isReady: Boolean, followers: List<User>?) {
        if (isReady){
            if (followers?.size == 0){
                binding.pb.visibility = View.INVISIBLE
                binding.rv.visibility = View.INVISIBLE
                binding.noFollow.text = resources.getString(R.string.zero_follower)
                binding.noFollow.visibility = View.VISIBLE
            } else {
                adapter.setData(followers)
                adapter.notifyDataSetChanged()
                binding.pb.visibility = View.INVISIBLE
                binding.noFollow.visibility = View.INVISIBLE
                binding.rv.visibility = View.VISIBLE
            }
        } else {
            binding.pb.visibility = View.INVISIBLE
            binding.rv.visibility = View.INVISIBLE
            binding.noFollow.text = resources.getString(R.string.no_connection)
            binding.noFollow.visibility = View.VISIBLE
        }
    }
}