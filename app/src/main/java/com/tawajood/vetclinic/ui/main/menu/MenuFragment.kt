package com.tawajood.vetclinic.ui.main.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentMenuBinding

import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import com.google.android.play.core.review.ReviewManager as ReviewManager1

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {


    private lateinit var binding: FragmentMenuBinding
    private lateinit var parent: MainActivity
    private val viewModel: MenuViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        onClick()
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.menuInfo.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        Glide.with(requireContext()).load(it.data!!.image).into(binding.imgClinic)
                        binding.tvName.text = it.data.name
                    }
                }
            }
        }
    }

    private fun onClick() {
        binding.clProfile.setOnClickListener {
            parent.navController.navigate(R.id.profileFragment)
        }
        binding.clAbout.setOnClickListener {
            parent.navController.navigate(R.id.aboutUsFragment)
        }
        binding.clTc.setOnClickListener {
            parent.navController.navigate(R.id.termsFragment)
        }
        binding.clSupport.setOnClickListener {
            parent.navController.navigate(R.id.supportFragment)
        }
        binding.btnMoney.setOnClickListener {
            parent.navController.navigate(R.id.paymentsFragment)
        }
        binding.clLogout.setOnClickListener {
            parent.logout()
        }
        binding.clNotification.setOnClickListener {
            parent.navController.navigate(R.id.notificationsFragment)
        }
        binding.clLanguage.setOnClickListener {
            parent.navController.navigate(R.id.settingsFragment)
        }
        binding.clOrders.setOnClickListener {
            parent.navController.navigate(R.id.myConsultantsFragment)
        }

        binding.clShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=com.tawajood.vetclinic"
            )
            startActivity(Intent.createChooser(intent, "Share With"))
        }

        binding.clRate.setOnClickListener {
            rateApp()
        }
    }

    private fun rateApp() {
        val manager = ReviewManagerFactory.create(requireContext())
        val request = manager.requestReviewFlow()

        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
            } else {
                @ReviewErrorCode val reviewErrorCode = (task.exception)
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.menu))
        parent.showBottomNav(true)
    }


}