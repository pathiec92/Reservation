package com.college.smartcertificate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.college.smartcertificate.MainActivity
import com.college.smartcertificate.databinding.FragmentHomeBinding
import com.college.smartcertificate.di.InjectorUtils

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    private val homeViewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
        homeViewModel.invalidCardDetected.observe(viewLifecycleOwner, Observer<Boolean>{
            if(it){
                (activity as MainActivity).invalidCardDetected()
            }
        })

        /* val textView: TextView = root.findViewById(R.id.text_home)
         homeViewModel.text.observe(this, Observer {
             textView.text = it
         })*/
        return binding.root
    }
}