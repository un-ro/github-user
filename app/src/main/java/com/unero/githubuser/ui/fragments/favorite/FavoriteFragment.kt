package com.unero.githubuser.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.R
import com.unero.githubuser.databinding.FragmentFavoriteBinding
import com.unero.githubuser.ui.adapter.favorite.FavoriteAdapter
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

        appbar()

        viewModel.listFav.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                adapter.apply {
                    setData(it)
                    notifyDataSetChanged()
                }
            } else {
                binding.apply {
                    ivNoData.visibility = View.VISIBLE
                    tvNoData.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun appbar() {
        binding.topbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_search -> {
                    true
                }
                R.id.item_delete_all -> {
                    viewModel.deleteAll()
                    Toasty.success(requireContext(), "Sukses delete all", Toasty.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRV() {
        adapter = FavoriteAdapter()
        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
    }
}