package com.tawajood.vetclinic.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.data.PrefsHelper
import com.tawajood.vetclinic.databinding.FragmentOTPBinding
import com.tawajood.vetclinic.databinding.FragmentRegisterBinding
import com.tawajood.vetclinic.pojo.RegisterBody
import com.tawajood.vetclinic.ui.auth.AuthActivity
import com.tawajood.vetclinic.ui.auth.AuthViewModel
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class OTPFragment : Fragment(R.layout.fragment_o_t_p) {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentOTPBinding
    private lateinit var parent: AuthActivity
    private var phone by Delegates.notNull<String>()
    private var ccp by Delegates.notNull<String>()
    private var name by Delegates.notNull<String>()
    private var email by Delegates.notNull<String>()
    private var address by Delegates.notNull<String>()
    private var regNum by Delegates.notNull<String>()

    private var check by Delegates.notNull<Int>()
    private var code = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOTPBinding.bind(requireView())
        parent = requireActivity() as AuthActivity
        name = requireArguments().getString(Constants.USERNAME).toString()
        ccp = requireArguments().getString(Constants.COUNTRY_CODE).toString()
        phone = requireArguments().getString(Constants.PHONE).toString()
        email = requireArguments().getString(Constants.EMAIL).toString()
        address = requireArguments().getString(Constants.ADDRESS).toString()
        regNum = requireArguments().getString(Constants.REG_NUM).toString()
        check = requireArguments().getInt(Constants.LOGIN)

        setupUI()
        onClick()
        observeData()
    }


    private fun onClick() {
        binding.activateBtn.setOnClickListener {
            if (check == 0) {
                if (code == binding.verCodeEt.text.toString()) {
                    viewModel.login(ccp, phone, "\$@#%12345AaBb\$@#%")
                } else {
                    ToastUtils.showToast(
                        requireContext(),
                        "الكود غير صحيح"
                    )
                }

            }

            if (check == 1) {
                if (code == binding.verCodeEt.text.toString()) {
                    viewModel.register(
                        RegisterBody(
                            name,
                            ccp,
                            phone,
                            email,
                            address,
                            regNum,
                            "\$@#%12345AaBb\$@#%"
                        )
                    )
                } else {
                    ToastUtils.showToast(
                        requireContext(),
                        "الكود غير صحيح"
                    )
                }

            }

        }

        binding.tvResend.setOnClickListener {
            viewModel.sendOtp(ccp, phone)
        }
    }

    private fun setupUI() {

        viewModel.sendOtp(ccp, phone)
        parent.imInOTP(true, ccp + phone)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sendOtp.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Idle -> {
                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        code = it.data!!.sent_code
                        binding.verCodeEt.setText(code)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userRegisterFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        PrefsHelper.setToken(it.data!!.token)

                        PrefsHelper.setFirst(false)

                        parent.gotoMain()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userLoginFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        PrefsHelper.setToken(it.data!!.token)

                        PrefsHelper.setFirst(false)

                        parent.gotoMain()
                    }
                }
            }
        }

    }


}