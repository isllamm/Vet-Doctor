package com.tawajood.vetclinic.ui.main.about_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentAboutUsBinding
import com.tawajood.vetclinic.databinding.FragmentMenuBinding
import com.tawajood.vetclinic.ui.auth.AuthViewModel
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AboutUsFragment : Fragment(R.layout.fragment_about_us) {


    private lateinit var binding: FragmentAboutUsBinding
    private lateinit var parent: MainActivity
    private val viewModel: AboutUsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutUsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.about.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        binding.tvAbout.text = it.data!!.about
                    }
                }
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.about_us))
        parent.showBottomNav(false)

    }


}