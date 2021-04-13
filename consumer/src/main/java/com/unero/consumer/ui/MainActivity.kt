package com.unero.consumer.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.unero.consumer.R
import com.unero.consumer.data.DataMapper
import com.unero.consumer.data.DatabaseContract.contentUri
import com.unero.consumer.databinding.ActivityMainBinding
import com.unero.consumer.ui.adapter.MyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        loadData()
        setupRV()

        binding.topbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.item_setting -> {
                    val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                    startActivity(mIntent)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            val mapping = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(contentUri, null, null, null, null)
                cursor?.let { DataMapper.mapCursorToList(it) }
            }
            val favorites = mapping.await()

            if (favorites != null) {
                if (favorites.isNotEmpty()) {
                    println(favorites.toString())
                    myAdapter.setData(favorites)
                } else {
                    showInfo()
                }
            }
        }
    }

    private fun showInfo() {
        binding.apply {
            ivIllustration.visibility = View.VISIBLE
            tvNoData.text = getString(R.string.no_data)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setupRV() {
        myAdapter = MyAdapter()

        binding.rv.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = myAdapter
        }
    }
}