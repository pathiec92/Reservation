package com.blue.goeat.ui.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.blue.goeat.R
import com.blue.goeat.databinding.FragmentHomeBinding
import com.blue.goeat.ui.main.ui.adapters.HotelAdapter
import com.blue.goeat.ui.main.ui.di.InjectorUtils

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory()
    }
    var hotelAdapter = HotelAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
       // binding.hotelList .apply { addItemDecoration(DividerItemDecoration(this.context,LinearLayoutManager.VERTICAL))  }
        binding.hotelList.adapter = hotelAdapter
        homeViewModel.hotels.observe(viewLifecycleOwner){ hotels ->
            hotelAdapter.submitList(hotels)
            showList()
        }
        homeViewModel.updateView()

        return binding.root
    }
    private fun showList() {
        binding.hotelList.visibility = View.VISIBLE
        binding.hotelList.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_up_slow))
    }


    private fun hideList() {
        binding.hotelList.visibility = View.GONE
    }
}