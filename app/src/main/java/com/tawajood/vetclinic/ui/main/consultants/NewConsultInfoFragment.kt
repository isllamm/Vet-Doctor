package com.tawajood.vetclinic.ui.main.consultants

import ResultDialogFragment
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
import com.tawajood.vetclinic.databinding.FragmentNewConsultInfoBinding
import com.tawajood.vetclinic.databinding.FragmentPreviousConsultInfoBinding
import com.tawajood.vetclinic.pojo.ConsultantInfo
import com.tawajood.vetclinic.pojo.Pet
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class NewConsultInfoFragment : Fragment(R.layout.fragment_new_consult_info) {


    private lateinit var binding: FragmentNewConsultInfoBinding
    private lateinit var parent: MainActivity
    private val viewModel: MyConsultantsViewModel by viewModels()
    private var id by Delegates.notNull<String>()
    private lateinit var consultantInfo: ConsultantInfo
    private lateinit var pet: Pet
    private lateinit var imagesAdapter: ClinicImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewConsultInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        id = requireArguments().getString(Constants.CONSULTANT_ID).toString()

        setupUI()
        observeData()
        onClick()
        setUpImages()
    }

    private fun setUpImages() {
        imagesAdapter = ClinicImagesAdapter()

        binding.rvImages.adapter = imagesAdapter
    }

    private fun onClick() {
        binding.tvPetDetails.setOnClickListener {
            parent.navController.navigate(
                R.id.previousAnimalInfoFragment,
                bundleOf(
                    Constants.PET_ID to pet.id.toString(),
                    Constants.TYPE to "2"
                )
            )
        }

        binding.tvReject.setOnClickListener {
            viewModel.rejectedConsultants(id)
        }
        binding.btnAccept.setOnClickListener {
            viewModel.acceptedConsultants(id)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newConsultantInfo.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        consultantInfo = it.data!!.newConsultants

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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.rejectedConsultant.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        ResultDialogFragment.newInstance(
                            getString(R.string.rejected_consultant), R.drawable.done
                        ).show(
                            parentFragmentManager,
                            ResultDialogFragment::class.java.canonicalName
                        )
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.acceptedConsultant.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        ResultDialogFragment.newInstance(
                            getString(R.string.accepted_consultant), R.drawable.done
                        ).show(
                            parentFragmentManager,
                            ResultDialogFragment::class.java.canonicalName
                        )
                    }
                }
            }
        }
    }

    private fun setupUI() {
        viewModel.getNewConsultantsInfo(id)
        parent.setTitle(getString(R.string.consultant_details))
        parent.showBottomNav(false)
    }
}