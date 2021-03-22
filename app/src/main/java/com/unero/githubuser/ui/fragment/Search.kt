package com.unero.githubuser.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.githubuser.R
import com.unero.githubuser.ui.adapter.SearchAdapter
import com.unero.githubuser.ui.viewmodel.SearchViewModel
import com.unero.githubuser.databinding.FragmentSearchBinding
import es.dmoral.toasty.Toasty

class Search : Fragment(), SearchView.OnCloseListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mViewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        mViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        render()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        showLoading(false)

        // Custom Toasty
        Toasty.custom(requireContext(), R.string.instruction, R.drawable.people_icon, R.color.toast ,Toasty.LENGTH_LONG, true, true).show()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pb.visibility = View.VISIBLE
        } else {
            binding.pb.visibility = View.GONE
        }
    }

    private fun render() {
        mViewModel.dataMLD.observe(this, {
            adapter.setData(it.items)
            showLoading(false)
        })
    }

    // Menu
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
                mViewModel.search(query)
                render()
                showLoading(false)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                showLoading(true)
                return false
            }
        })
        searchView.setIconifiedByDefault(true)
        searchView.setOnCloseListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mLanguage) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClose(): Boolean {
        Toast.makeText(context, "TOLONG", Toast.LENGTH_SHORT).show()
        showLoading(false)
        return true
    }
}