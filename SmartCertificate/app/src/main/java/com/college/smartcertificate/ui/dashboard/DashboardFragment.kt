package com.college.smartcertificate.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.college.smartcertificate.data.StudentData.isPhd
import com.college.smartcertificate.data.StudentData.studentEntity
import com.college.smartcertificate.databinding.FragmentDashboardBinding
import com.college.smartcertificate.databinding.PhdItemBinding
import com.college.smartcertificate.di.InjectorUtils
import com.college.smartcertificate.ui.dashboard.adapter.DashboardAdapter
import com.college.smartcertificate.ui.home.HomeViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var phdBinding: PhdItemBinding


    val dashboardAdapter = DashboardAdapter()
    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if(isPhd){
            phdBinding = PhdItemBinding.inflate(inflater, container, false)
            phdBinding.viewModel = homeViewModel
            phdBinding.root
        } else {
            binding = FragmentDashboardBinding.inflate(inflater, container, false)
            binding.viewModel = homeViewModel
            binding.markList.adapter = dashboardAdapter
            dashboardAdapter.submitList(List(1) { studentEntity })
            binding.root
        }
    }
}