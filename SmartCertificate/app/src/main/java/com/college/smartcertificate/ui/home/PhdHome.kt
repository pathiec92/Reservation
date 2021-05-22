package com.college.smartcertificate.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.college.smartcertificate.R
import com.college.smartcertificate.databinding.ActivityPhdHomeBinding
import com.college.smartcertificate.di.InjectorUtils

class PhdHome : AppCompatActivity() {
    private lateinit var binding: ActivityPhdHomeBinding


    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_phd_home)
        binding.viewModel = homeViewModel
    }
}
