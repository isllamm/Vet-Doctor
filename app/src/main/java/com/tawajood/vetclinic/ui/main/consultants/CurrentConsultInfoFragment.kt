package com.tawajood.vetclinic.ui.main.consultants

import android.os.Bundle
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
import com.tawajood.vetclinic.databinding.FragmentCurrentConsultInfoBinding
import com.tawajood.vetclinic.databinding.FragmentNewConsultInfoBinding
import com.tawajood.vetclinic.pojo.ConsultantInfo
import com.tawajood.vetclinic.pojo.Pet
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class CurrentConsultInfoFragment : Fragment(R.layout.fragment_current_consult_info) {


    private lateinit var binding: FragmentCurrentConsultInfoBinding
    private lateinit var parent: MainActivity
    private val viewModel: MyConsultantsViewModel by viewModels()
    private var id by Delegates.notNull<String>()
    private lateinit var consultantInfo: ConsultantInfo
    private lateinit var pet: Pet
    private lateinit var imagesAdapter: ClinicImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCurrentConsultInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        id = requireArguments().getString(Constants.CONSULTANT_ID).toString()
        setupUI()
        observeData()
        onClick()
        setUpImages()
    }

    private fun setupUI() {
        viewModel.getCurrentConsultantsInfo(id)
        parent.setTitle(getString(R.string.consultant_details))
        parent.showBottomNav(false)
    }

    private fun onClick() {
        binding.btnChat.setOnClickListener {
            parent.navController.navigate(R.id.chatFragment)
        }

        binding.tvAddDetails.setOnClickListener {
            parent.navController.navigate(
                R.id.addReportFragment, bundleOf(
                    Constants.CONSULTANT_ID to id
                )
            )
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentConsultantInfo.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        consultantInfo = it.data!!.currentConsultants

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

                        if (consultantInfo.paid == 0) {
                            binding.status.text = getText(R.string.current_consultant_not_paid)
                            binding.btnChat.isVisible = false
                        } else if (consultantInfo.paid == 1) {
                            binding.status.text = getText(R.string.current_consultant_paid)
                            binding.btnChat.isVisible = true

                        }

                    }
                }
            }
        }
    }

    private fun setUpImages() {
        imagesAdapter = ClinicImagesAdapter()

        binding.rvImages.adapter = imagesAdapter
    }


}