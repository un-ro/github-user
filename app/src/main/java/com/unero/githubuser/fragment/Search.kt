package com.unero.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.unero.githubuser.R
import com.unero.githubuser.databinding.FragmentDetailBinding
import com.unero.githubuser.databinding.FragmentSearchBinding

class Search : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.ll.setOnClickListener {
            it.findNavController().navigate(R.id.action_search_to_detail)
        }
        return binding.root
    }
}