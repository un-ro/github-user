package com.unero.githubuser.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.R
import com.unero.githubuser.databinding.FragmentSearchBinding
import com.unero.githubuser.ui.adapter.SearchAdapter
import com.unero.githubuser.ui.viewmodel.SearchViewModel
import es.dmoral.toasty.Toasty

class SearchFragment : Fragment(){

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mViewModel: SearchViewModel
    private var adapter = SearchAdapter()
    private var isConnected: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.rv.adapter = adapter
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(false)
        networkCheck()
        // Custom Toasty
        Toasty.custom(requireContext(), R.string.instruction, R.drawable.people_icon, R.color.toast ,Toasty.LENGTH_LONG, true, true).show()
    }

    private fun networkCheck() {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val actvieNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = actvieNetwork?.isConnectedOrConnecting == true
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pb.visibility = View.VISIBLE
        } else {
            binding.pb.visibility = View.GONE
        }
    }

    // Draw loading and result
    private fun render() {
        mViewModel.dataLD?.observe(this, {
            adapter.setData(it.items)
            adapter.notifyDataSetChanged()
            showLoading(false)
            Toasty.success(requireContext(), "${it.total_count} User", Toasty.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu, menu)

        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                networkCheck()
                return if (isConnected){
                    mViewModel.search(query)
                    render()
                    showLoading(false)
                    true
                } else {
                    Toasty.error(requireContext(), R.string.no_connection, Toasty.LENGTH_SHORT).show()
                    showLoading(false)
                    false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                showLoading(true)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mLanguage) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}