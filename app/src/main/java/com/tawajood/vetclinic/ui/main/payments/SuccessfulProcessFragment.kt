package com.tawajood.vetclinic.ui.main.payments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentSuccessfulProcessBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

class SuccessfulProcessFragment : Fragment(R.layout.fragment_successful_process) {


    private lateinit var binding: FragmentSuccessfulProcessBinding
    private lateinit var parent: MainActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSuccessfulProcessBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.payments))
        parent.showBottomNav(false)

    }
}