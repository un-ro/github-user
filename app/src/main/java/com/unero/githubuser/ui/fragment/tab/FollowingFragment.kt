package com.unero.githubuser.ui.fragment.tab

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
import com.unero.githubuser.databinding.FragmentFollowingBinding
import com.unero.githubuser.ui.adapter.FollowingAdapter
import com.unero.githubuser.ui.viewmodel.DetailViewModel
import es.dmoral.toasty.Toasty

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

    private lateinit var mViewModel: DetailViewModel
    private lateinit var binding: FragmentFollowingBinding
    private var adapter = FollowingAdapter()
    private var isConnected: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_following, container, false)
        binding.lifecycleOwner = this

        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        binding.pb.visibility = View.VISIBLE
        binding.rv.visibility = View.INVISIBLE
        binding.noFollow.visibility = View.INVISIBLE

        networkCheck()

        if (isConnected) {
            mViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            mViewModel.fetchFollowing(arguments?.getString(ARG_USERNAME)!!)
            render(true)
        } else {
            render(false)
        }
        return binding.root
    }

    private fun networkCheck() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: NetworkInfo? = cm.activeNetworkInfo
        isConnected = network?.isConnectedOrConnecting == true
    }

    private fun render(isReady: Boolean) {
        if (isReady){
            mViewModel.following?.observe(viewLifecycleOwner, {
                if (it.size == 0){
                    binding.pb.visibility = View.INVISIBLE
                    binding.rv.visibility = View.INVISIBLE
                    binding.noFollow.text = resources.getString(R.string.zero_follower)
                    binding.noFollow.visibility = View.VISIBLE
                } else {
                    adapter.setList(it)
                    adapter.notifyDataSetChanged()
                    binding.pb.visibility = View.INVISIBLE
                    binding.noFollow.visibility = View.INVISIBLE
                    binding.rv.visibility = View.VISIBLE
                }
            })
            Toasty.success(requireContext(), R.string.done, Toasty.LENGTH_SHORT).show()
        } else {
            binding.pb.visibility = View.INVISIBLE
            binding.rv.visibility = View.INVISIBLE
            binding.noFollow.text = resources.getString(R.string.no_connection)
            binding.noFollow.visibility = View.VISIBLE
        }
    }
}