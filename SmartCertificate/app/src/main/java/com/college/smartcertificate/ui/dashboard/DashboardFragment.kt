package com.college.smartcertificate.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.college.smartcertificate.data.StudentData.studentEntity
import com.college.smartcertificate.databinding.FragmentDashboardBinding
import com.college.smartcertificate.di.InjectorUtils
import com.college.smartcertificate.ui.dashboard.adapter.DashboardAdapter

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding


    val dashboardAdapter = DashboardAdapter()
    private val dashboardViewModel: DashboardViewModel by viewModels {
        InjectorUtils.provideDashboardViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.viewModel = dashboardViewModel
        binding.markList.adapter = dashboardAdapter
        dashboardAdapter.submitList(List(1){ studentEntity })
        return binding.root
    }
}