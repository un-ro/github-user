package com.unero.githubuser.ui.fragments.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.unero.githubuser.R
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.databinding.FragmentHomeBinding
import com.unero.githubuser.ui.adapter.shared.SharedAdapter
import de.mateware.snacky.Snacky
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val viewModel: HomeViewModel by sharedViewModel()
    private lateinit var adapter: SharedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topbar.setOnMenuItemClickListener { menuItem -> appbar(menuItem) }

        showIllustration(true)

        setupUI()

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Snacky.builder()
                .setView(requireView())
                .setText(it)
                .error()
                .show()
        })
    }

    private fun setupUI() {
        viewModel.listUser.observe(viewLifecycleOwner, {
            showIllustration(false)
            setupRV(it.items)
        })
    }

    private fun setupRV(items: List<User>) {
        adapter = SharedAdapter()
        adapter.setData(items)
        adapter.setFragment(this::class.java.simpleName)
        showLoading(false)
        binding.rv.visibility = View.VISIBLE
        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
    }

    private fun search(query: String) {
        viewModel.findUser(query)
        viewModel.listUser.observe(viewLifecycleOwner, {
            if (it.items.isNullOrEmpty()) {
                binding.rv.visibility = View.INVISIBLE
                showIllustration(true)
                showLoading(false)
                Snacky.builder()
                    .setView(requireView())
                    .setText(getString(R.string.no_result))
                    .warning()
                    .show()
            } else {
                setupRV(it.items)
                Snacky.builder()
                    .setView(requireView())
                    .setText(getString(R.string.total_result, it.total_count))
                    .success()
                    .show()
            }
        })
    }

    private fun appbar(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.item_search -> {

                val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                val searchView: SearchView = menuItem.actionView as SearchView

                searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                searchView.queryHint = resources.getString(R.string.search_hint)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        search(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        showLoading(true)
                        showIllustration(false)
                        return false
                    }
                })
                searchView.setOnCloseListener {
                    setupUI()
                    true
                }
                true
            }
            R.id.favorite -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                findNavController().navigate(action)
                true
            }
            R.id.item_setting -> {
                val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }

    private fun showLoading(state: Boolean) {
        binding.pb.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showIllustration(state: Boolean) {
        binding.animSearch.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}