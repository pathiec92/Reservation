package com.blue.goeat.ui.main.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.blue.goeat.databinding.FragmentAccountBinding
import com.blue.goeat.extentions.signOut
import com.blue.goeat.ui.main.ui.di.InjectorUtils

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    private val accountViewModel: AccountViewModel by viewModels {
        InjectorUtils.provideAccountViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        binding.viewModel = accountViewModel
        binding.logOut.setOnClickListener {
            activity?.signOut()
        }
        accountViewModel.updateView()
        return binding.root

    }
}