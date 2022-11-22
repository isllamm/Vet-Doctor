package com.tawajood.vetclinic.ui.main.chat

import ToastUtils
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.MessagesAdapter
import com.tawajood.vetclinic.databinding.FragmentChatBinding
import com.tawajood.vetclinic.pojo.Message
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import kotlin.properties.Delegates

@AndroidEntryPoint
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var adapter: MessagesAdapter
    private lateinit var binding: FragmentChatBinding
    private lateinit var parent: MainActivity
    private val viewModel: ChatViewModel by viewModels()
    private var requestId by Delegates.notNull<String>()
    private var image: File? = null
    private var messages = mutableListOf<Message>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        //requestId = requireArguments().getString(Constants.CONSULTANT_ID).toString()

        viewModel.getChat("1","12")

        setupUI()
        observeData()
        onClick()
        setupChats()
    }

    private fun onClick() {
        binding.refresher.setOnRefreshListener {
            viewModel.getChat("1","12")
        }

        binding.sendImg.setOnClickListener {
            if (!TextUtils.isEmpty(binding.messageEt.text) || image != null) {
                viewModel.sendMessage("1","12", binding.messageEt.text.toString(), "0")



                binding.messagesRv.scrollToPosition(adapter.messages.size - 1)
                binding.messageEt.setText("")
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.chat))
        parent.showBottomNav(false)
    }

    private fun setupChats() {
        adapter = MessagesAdapter(object : MessagesAdapter.OnItemClick {
            override fun onImageClickListener(position: Int) {

            }

        })
        binding.messagesRv.adapter = adapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getChat.collectLatest {
                binding.refresher.isRefreshing = false
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {
                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        messages = it.data!!.chat[0].messages
                        adapter.messages = messages
                        binding.messagesRv.scrollToPosition(messages.size - 1)
                        image = null
                        binding.imageCard.isVisible = false

                    }
                }
            }
        }


    }


}