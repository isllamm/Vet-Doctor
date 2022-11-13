package com.tawajood.vetclinic.ui.main.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentAboutUsBinding
import com.tawajood.vetclinic.databinding.FragmentSupportBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.about_us.AboutUsViewModel
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SupportFragment : Fragment(R.layout.fragment_support) {

    private lateinit var binding: FragmentSupportBinding
    private lateinit var parent: MainActivity
    private val viewModel: SupportViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSupportBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.contact.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        binding.tvPhone.text = it.data!!.contact_us.phone
                        binding.tvMail.text = it.data!!.contact_us.complaint_email

                    }
                }
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.technical_support))
        parent.showBottomNav(false)

    }


}