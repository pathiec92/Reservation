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
import com.blue.goeat.ui.main.ui.adapters.DishOrderAdapter
import com.blue.goeat.di.InjectorUtils
import com.blue.goeat.ui.main.ui.order.OrderWiz

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory()
    }
    var dishOderAdapter = DishOrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
       //binding.dishOrderList .apply { addItemDecoration(DividerItemDecoration(this.context,LinearLayoutManager.VERTICAL))  }
       binding.dishOrderList.adapter = dishOderAdapter
        homeViewModel.dishOrder.observe(viewLifecycleOwner){ orders ->
            dishOderAdapter.submitList(orders)
            showList()
        }
        homeViewModel.updateView()

        return binding.root
    }
    private fun showList() {
       binding.dishOrderList.visibility = View.VISIBLE
       binding.dishOrderList.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_up_slow))
    }


    private fun hideList() {
       binding.dishOrderList.visibility = View.GONE
    }
}