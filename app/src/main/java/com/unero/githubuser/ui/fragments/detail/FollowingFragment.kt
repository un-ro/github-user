package com.unero.githubuser.ui.fragments.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.R
import com.unero.githubuser.RemoteViewModel
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.FragmentFollowingBinding
import com.unero.githubuser.ui.adapter.SharedAdapter

class FollowingFragment : Fragment() {

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: RemoteViewModel
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: SharedAdapter
    private var isConnected: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RemoteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        binding.lifecycleOwner = this

        setupRV()

        binding.rv.visibility = View.INVISIBLE
        binding.noFollow.visibility = View.INVISIBLE

        viewModel.listFollowing.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                val following = it.body()
                if (following != null) {
                    render(true, following)
                }
            }
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

    private fun networkCheck() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: NetworkInfo? = cm.activeNetworkInfo
        isConnected = network?.isConnectedOrConnecting == true
    }

    private fun render(isReady: Boolean, following: List<User>?) {
        if (isReady){
            if (following?.size == 0){
                binding.pb.visibility = View.INVISIBLE
                binding.rv.visibility = View.INVISIBLE
                binding.noFollow.text = resources.getString(R.string.zero_follower)
                binding.noFollow.visibility = View.VISIBLE
            } else {
                adapter.setData(following)
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