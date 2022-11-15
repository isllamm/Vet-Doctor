package com.tawajood.vetclinic.ui.main.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentDeleteAccountDialogBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DeleteAccountDialogFragment() :
    DialogFragment(R.layout.fragment_delete_account_dialog) {
    companion object {
        fun newInstance(): DeleteAccountDialogFragment {
            return DeleteAccountDialogFragment()
        }
    }


    private lateinit var binding: FragmentDeleteAccountDialogBinding
    private lateinit var parent: MainActivity
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteAccountDialogBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corners_dialog);

        onClick()
        observeData()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupUI()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.deleteAccount.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        parent.logout()
                    }
                }
            }
        }
    }

    private fun onClick() {
        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteAccount()
        }
    }

    private fun setupUI() {
        val width = (resources.displayMetrics.widthPixels * 0.88).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}