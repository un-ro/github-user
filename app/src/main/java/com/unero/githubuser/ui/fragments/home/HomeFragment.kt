package com.unero.githubuser.ui.fragments.home

import android.app.SearchManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.unero.githubuser.R
import com.unero.githubuser.RemoteViewModel
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.FragmentHomeBinding
import com.unero.githubuser.ui.adapter.SharedAdapter
import de.mateware.snacky.Snacky

class HomeFragment : Fragment() {

    private lateinit var viewModel: RemoteViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: SharedAdapter
    private var isConnected: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(RemoteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finishAffinity()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(false)
        setupRV()
        appbar()

        binding.materialButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun setupRV() {
        adapter = SharedAdapter()
        adapter.setFragment(this::class.java.simpleName)
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
    }

    private fun networkCheck() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val actvieNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = actvieNetwork?.isConnectedOrConnecting == true
    }

    private fun render(query: String) {
        viewModel.findUser(query)
        viewModel.listUser.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                showLoading(false)
                val total = it.body()?.total_count
                val listUser: List<User>? = it.body()?.items
                adapter.setData(listUser)
                adapter.notifyDataSetChanged()
                if (total == 0) {
                    zeroResult()
                } else {
                    illustration(false)
                    Snacky.builder()
                        .setView(requireView())
                        .setText("Found $total Users")
                        .success()
                        .show()
                }
            } else {
                Snacky.builder()
                    .setView(requireView())
                    .setText(it.errorBody().toString())
                    .error()
                    .show()
            }
        })
    }

    private fun zeroResult(){
        illustration(true)
        binding.ivIllustration.setImageResource(R.drawable.undraw_lost_bqr2)
        binding.tvDescHelp.text = "Not found any User"
    }

    private fun appbar(){
        binding.topbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_search -> {

                    val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                    val searchView: SearchView = it.actionView as SearchView

                    searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                    searchView.queryHint = resources.getString(R.string.search_hint)
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            render(query)
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (newText.isNullOrBlank())
                                showLoading(false)
                            else
                                showLoading(true)

                            return false
                        }
                    })
                    true
                }
                R.id.item_setting -> {
                    findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pb.visibility = View.VISIBLE
            illustration(false)
        } else {
            binding.pb.visibility = View.GONE
        }
    }

    private fun illustration(switch: Boolean){
        if (switch) {
            binding.ivIllustration.visibility = View.VISIBLE
            binding.tvDescHelp.visibility = View.VISIBLE
        } else {
            binding.ivIllustration.visibility = View.GONE
            binding.tvDescHelp.visibility = View.GONE
        }
    }
}