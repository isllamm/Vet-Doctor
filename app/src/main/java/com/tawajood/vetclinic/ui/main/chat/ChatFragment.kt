package com.tawajood.vetclinic.ui.main.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.NotificationAdapter
import com.tawajood.vetclinic.databinding.FragmentChatBinding
import com.tawajood.vetclinic.databinding.FragmentNotificationsBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.notifications.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {


    private lateinit var binding: FragmentChatBinding
    private lateinit var parent: MainActivity
    private val viewModel: ChatViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()

    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.chat))
        parent.showBottomNav(false)
    }

    private fun observeData() {

    }


}