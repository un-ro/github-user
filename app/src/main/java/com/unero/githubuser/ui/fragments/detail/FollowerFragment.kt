package com.unero.githubuser.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.R
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.FragmentFollowerBinding
import com.unero.githubuser.ui.adapter.shared.SharedAdapter

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding as FragmentFollowerBinding

    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: SharedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

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
                binding.apply {
                    pb.visibility = View.INVISIBLE
                    rv.visibility = View.INVISIBLE
                    noFollow.text = resources.getString(R.string.zero_follower)
                    noFollow.visibility = View.VISIBLE
                }
            } else {
                adapter.setData(followers)
                adapter.notifyDataSetChanged()
                binding.pb.visibility = View.INVISIBLE
                binding.noFollow.visibility = View.INVISIBLE
                binding.rv.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                pb.visibility = View.INVISIBLE
                rv.visibility = View.INVISIBLE
                noFollow.text = resources.getString(R.string.no_connection)
                noFollow.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}