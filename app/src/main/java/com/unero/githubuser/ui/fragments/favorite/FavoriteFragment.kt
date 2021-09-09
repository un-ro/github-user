package com.unero.githubuser.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.unero.githubuser.R
import com.unero.githubuser.data.local.model.Favorite
import com.unero.githubuser.databinding.FragmentFavoriteBinding
import com.unero.githubuser.ui.adapter.favorite.FavoriteAdapter
import es.dmoral.toasty.Toasty
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    private val viewModel by viewModel<FavoriteViewModel>()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appbar()
        viewModel.listFav.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                setupRV(it)
            } else {
                binding.apply {
                    animNoItem.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun appbar() {
        binding.topbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.topbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_delete_all -> {
                    viewModel.deleteAll()
                    Toasty.success(requireContext(), getString(R.string.toast_delete_all), Toasty.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRV(list: List<Favorite>) {
        adapter = FavoriteAdapter()
        adapter.setData(list)
        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}