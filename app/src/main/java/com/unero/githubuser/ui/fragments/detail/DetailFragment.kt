package com.unero.githubuser.ui.fragments.detail

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.unero.githubuser.R
import com.unero.githubuser.RemoteViewModel
import com.unero.githubuser.ui.adapter.SectionsPagerAdapter
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.databinding.FragmentDetailBinding
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
    private lateinit var viewModel: RemoteViewModel
    private var isConnected: Boolean = true
    private var username = ""
    private lateinit var profile: Profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RemoteViewModel::class.java)

        username = arguments?.getString("id").orEmpty()
        fetch(username)
    }

    private fun fetch(username: String){
        viewModel.findDetail(username)
        viewModel.listFollow(username)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                profile = it.body()!!
                binding.profile = profile
                drawIcon(profile)
                binding.topbar.title = "${profile.login} Profile\'s"
            }
        })

        // Tab Layout
        drawTab()
        appbar()
    }

    private fun appbar() {
        binding.topbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detail_to_homeFragment)
        }

        binding.topbar.setOnMenuItemClickListener {
            when (it.itemId) {
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
                R.id.btn_fav -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun drawTab(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos])
        }.attach()
    }

    private fun networkCheck() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: NetworkInfo? = cm.activeNetworkInfo
        isConnected = network?.isConnectedOrConnecting == true
    }

    private fun drawIcon(profile: Profile) {
        binding.pb.visibility = View.GONE
        Picasso.get().load(profile.avatar_url).into(binding.imgAvatar)
        val repo = resources.getString(R.string.repo_value, profile.public_repos)
        binding.tvRepo.text = repo
        binding.tvRepo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_folder, 0, 0, 0)
        if (profile.location != null)
            binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_map, 0, 0, 0)
        if (profile.company != null)
            binding.tvCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_work, 0, 0, 0)

        val tvFollower = resources.getString(R.string.dynamic_tab_1, profile.followers)
        val tvFollowing = resources.getString(R.string.dynamic_tab_2, profile.following)
        binding.tabs.getTabAt(0)?.text = tvFollower
        binding.tabs.getTabAt(1)?.text = tvFollowing
    }
}