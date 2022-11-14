package com.tawajood.vetclinic.ui.main.consultants

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.ClinicImagesAdapter
import com.tawajood.vetclinic.databinding.FragmentMyConsultantsBinding
import com.tawajood.vetclinic.databinding.FragmentPreviousConsultInfoBinding
import com.tawajood.vetclinic.pojo.Consultant
import com.tawajood.vetclinic.pojo.ConsultantInfo
import com.tawajood.vetclinic.pojo.Pet
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class PreviousConsultInfoFragment : Fragment(R.layout.fragment_previous_consult_info) {

    private lateinit var binding: FragmentPreviousConsultInfoBinding
    private lateinit var parent: MainActivity
    private val viewModel: MyConsultantsViewModel by viewModels()
    private var id by Delegates.notNull<String>()
    private lateinit var consultantInfo: ConsultantInfo
    private lateinit var pet: Pet
    private lateinit var imagesAdapter: ClinicImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPreviousConsultInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        id = requireArguments().getString(Constants.CONSULTANT_ID).toString()
        setupUI()
        observeData()
        onClick()
        setUpImages()
    }
    private fun onClick() {
        binding.ivLocation.setOnClickListener {

        }

        binding.tvPetDetails.setOnClickListener {
            parent.navController.navigate(
                R.id.previousAnimalInfoFragment,
                bundleOf(Constants.PET_ID to pet.id.toString(), Constants.CONSULTANT_ID to consultantInfo.id.toString())
            )
        }
    }
    private fun setUpImages() {
        imagesAdapter = ClinicImagesAdapter()

        binding.rvImages.adapter = imagesAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.previousConsultantInfo.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        consultantInfo = it.data!!.previousConsultants

                        binding.tvNumber.text = consultantInfo.id.toString()

                        binding.tvDate.text = consultantInfo.date

                        binding.tvTime.text = consultantInfo.time

                        binding.tvType.text = consultantInfo.requestType

                        binding.tvDuration.text = consultantInfo.lat

                        if (consultantInfo.images.isNotEmpty()) {
                            binding.rvImages.isVisible = true
                            binding.tv9.isVisible = true
                            imagesAdapter.images = consultantInfo.images
                        } else {
                            binding.rvImages.isVisible = false
                            binding.tv9.isVisible = false
                        }

                        pet = consultantInfo.pet
                        Glide.with(requireContext()).load(pet.image)
                            .into(binding.ivPet)
                        binding.tvPetName.text = pet.name

                        binding.tvNotes.text = consultantInfo.notes

                    }
                }
            }
        }
    }



    private fun setupUI() {
        viewModel.getPreviousConsultantsInfo(id)
        parent.setTitle(getString(R.string.consultant_details))
        parent.showBottomNav(false)
    }


}