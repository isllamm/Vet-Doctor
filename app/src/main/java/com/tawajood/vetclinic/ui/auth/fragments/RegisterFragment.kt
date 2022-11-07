package com.tawajood.vetclinic.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentLoginBinding
import com.tawajood.vetclinic.databinding.FragmentRegisterBinding
import com.tawajood.vetclinic.pojo.RegisterBody
import com.tawajood.vetclinic.ui.auth.AuthActivity
import com.tawajood.vetclinic.ui.auth.AuthViewModel
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {


    private lateinit var binding: FragmentRegisterBinding
    private lateinit var parent: AuthActivity
    private lateinit var registerBody: RegisterBody

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(requireView())
        parent = requireActivity() as AuthActivity

        setupUI()
        onClick()
        observeData()
    }


    private fun onClick() {
        binding.btnCheck.setOnClickListener {
            viewModel.checkPhone(
                "+"+binding.ccp.selectedCountryCode.toString(),
                binding.phoneEt.text.toString()
            )
        }
    }

    private fun setupUI() {
        parent.imInOTP(false)
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.checkPhone.collectLatest {
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
                        Log.d("islam", "observeData: "+it.message)
                        if (it.data != null) {
                            ToastUtils.showToast(
                                requireContext(),
                                it.data.message
                            )
                        }
                    }
                }
            }
        }
    }


}