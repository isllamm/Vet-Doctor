package com.tawajood.vetclinic.ui.main.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.NotificationAdapter
import com.tawajood.vetclinic.adapters.SpecializationAdapter
import com.tawajood.vetclinic.databinding.FragmentNotificationsBinding
import com.tawajood.vetclinic.databinding.FragmentPaymentsBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.payments.PaymentsViewModel
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var parent: MainActivity
    private val viewModel: NotificationsViewModel by viewModels()
    private lateinit var notificationAdapter: NotificationAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()
        setupNotification()

    }

    private fun setupNotification() {
        notificationAdapter = NotificationAdapter()

        binding.rvNotifications.adapter = notificationAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notifications.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        notificationAdapter.notifications = it.data!!.notifications.data
                    }
                }
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.notifications))
        parent.showBottomNav(true)
    }


}