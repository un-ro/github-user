package com.unero.githubuser.ui.fragments.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.unero.githubuser.R
import com.unero.githubuser.data.local.Favorite
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.databinding.FragmentDetailBinding
import com.unero.githubuser.ui.adapter.SectionsPagerAdapter
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class DetailFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var profile: Profile

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        username = arguments?.getString("username").orEmpty()
        fetch(username)
        viewModel.setStatus(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        setFavorite(username) // Set status false / true from username
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                profile = it.body()!!
                profile = editProfile(profile)
                binding.profile = profile
                drawIcon(profile)
                binding.topbar.title = "${profile.login} Profile\'s"
                setupFAB()
            }
        })

        drawTab()
        appbar()
    }

    private fun editProfile(profile: Profile): Profile {
        // Change profile value if there are null values
        profile.apply {
            if (company != null) company = buildString {
                append(company!!.take(15))
                append("...")
            }
            if (location == null) location = "Not Included"
            if (company == null) company = "Not Included"
        }

        return profile
    }

    private fun setupFAB() {
        // Create object for insert
        val favorite = profile.login?.let { Favorite(
                0, it, profile.name.orEmpty(), profile.avatar_url.orEmpty(),
                profile.followers ?: 0, profile.following ?: 0)
        }

        // Observe status value, change drawable and database action
        viewModel.status.observe(viewLifecycleOwner, {
            if (it) {
                binding.fabFav.apply {
                    setImageResource(R.drawable.ic_fav_filled)
                    setOnClickListener {
                        viewModel.delete(username)
                        viewModel.setStatus(false)
                        showToast("delete")
                    }
                }
            } else {
                    binding.fabFav.apply {
                        setImageResource(R.drawable.ic_fav)
                        setOnClickListener {
                            viewModel.add(favorite!!)
                            viewModel.setStatus(true)
                            showToast("insert")
                        }
                    }
            }
        })
    }

    private fun showToast(s: String) {
        when (s) {
            "insert" -> {
                Toasty.success(requireContext(), getString(R.string.toast_insert), Toasty.LENGTH_SHORT).show()
            }
            "delete" -> {
                Toasty.success(requireContext(), getString(R.string.toast_delete), Toasty.LENGTH_SHORT).show()
            }
        }
    }

    // Get data from api
    private fun fetch(username: String){
        viewModel.findDetail(username)
        viewModel.listFollow(username)
    }

    // Material Appbar
    private fun appbar() {
        binding.topbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detail_to_homeFragment)
        }

        binding.topbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                // Share method, text value to Implicit Intent
                R.id.btn_share -> {
                    val paragraph = """
                        Hey I found cool user ${profile.login} from Github User App
                        Check it out ${profile.html_url}
                    """.trimIndent()
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
                        data = profile.html_url?.toUri()
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    // TabLayout and ViewPager
    private fun drawTab(){
        binding.viewPager.adapter = SectionsPagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()
    }

    // Changing UI like icon, text from object
    private fun drawIcon(profile: Profile) {
        // Download image from url and set text from object value
        Glide.with(this).load(profile.avatar_url).into(binding.imgAvatar)
        val repo = resources.getString(R.string.repo_value, profile.public_repos)
        val tvFollower = resources.getString(R.string.dynamic_tab_1, profile.followers)
        val tvFollowing = resources.getString(R.string.dynamic_tab_2, profile.following)

        // Binding
        binding.apply {
            pb.visibility = View.GONE
            binding.tvRepo.text = repo
            binding.tvRepo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_folder, 0, 0, 0)
            if (profile.location != null)
                binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_map, 0, 0, 0)
            if (profile.company != null)
                binding.tvCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_work, 0, 0, 0)
            binding.tabs.getTabAt(0)?.text = tvFollower
            binding.tabs.getTabAt(1)?.text = tvFollowing
        }
    }

    // Change status value from viewmodel from search Favorite
    private fun setFavorite(username: String) {
        viewModel.search(username).observe(viewLifecycleOwner, {
            viewModel.setStatus(it != null)
        })
    }
}