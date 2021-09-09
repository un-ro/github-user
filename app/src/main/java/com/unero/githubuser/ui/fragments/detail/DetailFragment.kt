package com.unero.githubuser.ui.fragments.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.unero.githubuser.R
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.databinding.FragmentDetailBinding
import com.unero.githubuser.ui.adapter.SectionsPagerAdapter
import com.unero.githubuser.util.Mapper
import es.dmoral.toasty.Toasty

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        viewModel.findDetail(args.username)
        viewModel.setStatus(false)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = DetailFragmentDirections.actionDetailToHomeFragment()
                findNavController().navigate(action)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFavorite(args.username) // Set status false / true from username

        viewModel.profile.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                val data = it.body()
                if (data != null) {
                    setAppBar(data)
                    setupUI(data)
                    setupFAB(data)
                }
            }
        })
    }

    private fun setupUI(data: Profile) {
        binding.apply {
            collapsingBar.title = data.username

            Glide.with(root)
                .load(data.avatar)
                .into(ivBar)

            content.tvName.text = if (data.name.isNullOrEmpty()) data.username else data.name

            content.chipLocation.text =
                if (data.location.isNullOrEmpty()) "N/A"
                else data.location

            content.chipCompany.text =
                if (data.company.isNullOrEmpty()) "N/A"
                else data.company

            content.chipRepo.text =
                if (data.repository >= 1) "Repo Publik ${data.repository}"
                else "Repo Publik 0"

            content.viewPager.adapter = SectionsPagerAdapter(this@DetailFragment)
            TabLayoutMediator(binding.content.tabs, binding.content.viewPager) { tab, pos ->
                when (pos) {
                    0 -> { tab.text = "Follower ${data.followers}"}
                    1 -> { tab.text = "Following ${data.following}"}
                }
            }.attach()
        }
    }

    private fun setupFAB(data: Profile) {
        // Create object for insert
         val favorite = Mapper.profileToFavorite(data)

        // Observe status value, change drawable and database action
        viewModel.status.observe(viewLifecycleOwner, {
            if (it) {
                binding.fabFav.apply {
                    setImageResource(R.drawable.ic_fav_filled)
                    setOnClickListener {
                        viewModel.delete(args.username)
                        viewModel.setStatus(false)
                        showToast("delete")
                    }
                }
            } else {
                    binding.fabFav.apply {
                        setImageResource(R.drawable.ic_fav)
                        setOnClickListener {
                            viewModel.add(favorite)
                            viewModel.setStatus(true)
                            showToast("insert")
                        }
                    }
            }
        })
    }

    private fun showToast(s: String) {
        Toasty.success(requireContext(), s, Toasty.LENGTH_SHORT).show()
    }

    // Material Appbar
    private fun setAppBar(profile: Profile) {
        Glide.with(binding.root)
            .load(profile.avatar)
            .into(binding.ivBar)
        binding.toolbar.apply {
            title = profile.username
            setNavigationOnClickListener {
                val action = DetailFragmentDirections.actionDetailToHomeFragment()
                findNavController().navigate(action)
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    // Share method, text value to Implicit Intent
                    R.id.btn_share -> {
                        val paragraph = getString(R.string.share_paragraph, profile.username, profile.githubUrl)
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, paragraph)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(intent, null)
                        startActivity(shareIntent)
                        true
                    }
                    // Intent go to Browser with link to Account Site
                    R.id.btn_link -> {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = profile.githubUrl.toUri()
                        }
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    // Change status value from View Model from search Favorite
    private fun setFavorite(username: String) {
        viewModel.search(username).observe(viewLifecycleOwner, {
            viewModel.setStatus(it != null)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}