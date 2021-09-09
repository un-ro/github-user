package com.unero.githubuser.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.FragmentFollowingBinding
import com.unero.githubuser.ui.adapter.shared.SharedAdapter

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding

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
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        viewModel.listFollowing.observe(viewLifecycleOwner, {
            showLoading(false)
            if (it.isSuccessful) {
                val data = it.body()
                if (!data.isNullOrEmpty()) {
                    setupRV(data)
                    showNoData(false)
                } else {
                    showNoData(true)
                }
            }
        })
    }

    private fun showNoData(condition: Boolean) {
        binding.animNoItem.visibility = if (condition) View.VISIBLE else View.GONE
        binding.rv.visibility = if(condition) View.GONE else View.VISIBLE
    }

    private fun showLoading(condition: Boolean) {
        binding.pb.visibility = if (condition) View.VISIBLE else View.GONE
    }

    private fun setupRV(data: List<User>) {
        adapter = SharedAdapter()
        adapter.setFragment(this::class.java.simpleName)
        adapter.setData(data)
        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}