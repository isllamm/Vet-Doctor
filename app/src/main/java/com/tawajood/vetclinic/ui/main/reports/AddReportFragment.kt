package com.tawajood.vetclinic.ui.main.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.NotificationAdapter
import com.tawajood.vetclinic.databinding.FragmentAddReportBinding
import com.tawajood.vetclinic.databinding.FragmentNotificationsBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.notifications.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReportFragment : Fragment(R.layout.fragment_add_report) {


    private lateinit var binding: FragmentAddReportBinding
    private lateinit var parent: MainActivity
    private val viewModel: AddReportViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddReportBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()

    }

    private fun setupUI() {
        parent.showBottomNav(false)
        parent.setTitle(getString(R.string.add_report))
    }

    private fun observeData() {

    }


}