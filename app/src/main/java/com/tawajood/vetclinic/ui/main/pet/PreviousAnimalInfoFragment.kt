package com.tawajood.vetclinic.ui.main.pet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentPreviousAnimalInfoBinding
import com.tawajood.vetclinic.databinding.FragmentProfileBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.profile.ProfileViewModel
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class PreviousAnimalInfoFragment : Fragment(R.layout.fragment_previous_animal_info) {


    private lateinit var binding: FragmentPreviousAnimalInfoBinding
    private lateinit var parent: MainActivity
    private val viewModel: AnimalViewModel by viewModels()
    private var id by Delegates.notNull<String>()
    private var requestId by Delegates.notNull<String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPreviousAnimalInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        id = requireArguments().getString(Constants.PET_ID).toString()
        requestId = requireArguments().getString(Constants.CONSULTANT_ID).toString()
        Log.d("islam", "observeData: ${id}")
        Log.d("islam", "observeData: ${requestId}")

        setupUI()
        observeData()

    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.previousAnimalInfo.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        Log.d("islam", "observeData: ${it.data!!.animalInfo}")

                    }
                }
            }
        }
    }

    private fun setupUI() {
        viewModel.getPreviousAnimalInfo(id, requestId)
        parent.setTitle(getString(R.string.pet_info))
        parent.showBottomNav(false)
    }
}