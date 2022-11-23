package com.tawajood.vetclinic.ui.main.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.ClinicImagesAdapter
import com.tawajood.vetclinic.adapters.ClinicTimesAdapter
import com.tawajood.vetclinic.adapters.SpecializationAdapter
import com.tawajood.vetclinic.databinding.FragmentPaymentsBinding
import com.tawajood.vetclinic.databinding.FragmentProfileBinding
import com.tawajood.vetclinic.pojo.Bank
import com.tawajood.vetclinic.pojo.ClinicDay
import com.tawajood.vetclinic.pojo.ShowTimes
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.payments.PaymentsViewModel
import com.tawajood.vetclinic.utils.Resource
import com.tawajood.vetclinic.utils.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var parent: MainActivity
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var spAdapter: SpecializationAdapter
    private lateinit var timesAdapter: ClinicTimesAdapter
    private lateinit var imagesAdapter: ClinicImagesAdapter
    private var showTimes = mutableListOf<ShowTimes>()
    private var days = mutableListOf<ClinicDay>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        viewModel.getProfile()
        setupUI()
        onClick()
        observeData()
        setUpSpecialization()
        setUpClinicTimes()
        setUpClinicImages()
    }

    private fun setUpClinicImages() {
        imagesAdapter = ClinicImagesAdapter()

        binding.rvImages.adapter = imagesAdapter
    }

    private fun setUpClinicTimes() {
        timesAdapter = ClinicTimesAdapter()

        binding.rvTimes.adapter = timesAdapter
    }

    private fun setUpSpecialization() {
        spAdapter = SpecializationAdapter()

        binding.rvSpecializations.adapter = spAdapter
    }

    private fun onClick() {

        binding.btnEdit.setOnClickListener {
            parent.navController.navigate(R.id.editProfileFragment)
        }

        binding.star.setOnClickListener {
            parent.navController.navigate(R.id.reviewsFragment)
        }

        binding.tvDeleteAccount.setOnClickListener {
            showAlertDialog(childFragmentManager)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.profile.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        val profile = it.data!!
                        Glide.with(requireContext()).load(profile.image).into(binding.imgClinic)
                        binding.tvFees.text =
                            profile.consultation_fees.toString() + " " + getString(R.string.Rs)
                        binding.tvDuration.text =
                            profile.consultation_duration + " " + getString(R.string.duration1)
                        binding.tvName.text = profile.name
                        binding.tvNumOfRates.text = "(" + profile.count_clinic_rate.toString() + ")"
                        binding.tvRating.text = profile.rate.toString()
                        binding.tvDetails.text = profile.details
                        binding.clinicNameEt.text = profile.name
                        binding.emailEt.text = profile.email
                        binding.phoneEt.text = profile.phone
                        binding.ccp.setCountryForPhoneCode(profile.country_code.toInt())
                        binding.licenseNumEt.text = profile.registration_number
                        binding.addressEt.text = profile.address
                        days = profile.clinic_days
                        spAdapter.specialization = profile.specializations
                        imagesAdapter.images = profile.clinic_images

                        if (days.isNotEmpty()) {
                            showTimes()
                        }


                        PrefsHelper.setUserImage(profile.image)
                        PrefsHelper.setUserId(profile.id)
                        PrefsHelper.setUsername(profile.name)
                        PrefsHelper.setEmail(profile.email)
                        PrefsHelper.setCurrentLat(profile.lat.toFloat())
                        PrefsHelper.setCurrentLng(profile.lng.toFloat())
                        PrefsHelper.setPhone(profile.phone)
                        PrefsHelper.setCountryCode(profile.country_code)

                    }
                }
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.profile))
        parent.showBottomNav(true)


    }

    private fun showTimes() {
        showTimes.clear()
        for (item in days) {
            for (time in item.times) {
                showTimes.add(ShowTimes(time.id, item.day.name, time.from, time.to))
            }
        }
        timesAdapter.times = showTimes
        //Log.d("islam", "showTimes: " + showTimes)
    }
}