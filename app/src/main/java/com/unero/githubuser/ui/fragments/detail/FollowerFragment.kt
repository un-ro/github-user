package com.unero.githubuser.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.FragmentFollowerBinding
import com.unero.githubuser.ui.adapter.shared.SharedAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding as FragmentFollowerBinding

    private val viewModel by sharedViewModel<DetailViewModel>()
    private lateinit var adapter: SharedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        viewModel.listFollower.observe(viewLifecycleOwner, {
            showLoading(false)
            if (!it.isNullOrEmpty()) {
                setupRV(it)
                showNoData(false)
            } else {
                showNoData(true)
            }
        })
    }

    private fun showNoData(condition: Boolean) {
        binding.animNoItem.visibility = if (condition) View.VISIBLE else View.GONE
        binding.rv.visibility = if (condition) View.GONE else View.VISIBLE
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