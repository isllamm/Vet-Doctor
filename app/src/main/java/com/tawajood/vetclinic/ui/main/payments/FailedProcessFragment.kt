package com.tawajood.vetclinic.ui.main.payments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentFailedProcessBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

class FailedProcessFragment : Fragment(R.layout.fragment_failed_process) {


    private lateinit var binding: FragmentFailedProcessBinding
    private lateinit var parent: MainActivity


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFailedProcessBinding.bind(requireView())
        parent = requireActivity() as MainActivity


        setupUI()
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.payments))
        parent.showBottomNav(false)

    }


}