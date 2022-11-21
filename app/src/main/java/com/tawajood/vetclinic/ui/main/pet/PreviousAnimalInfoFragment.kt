package com.tawajood.vetclinic.ui.main.pet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.*
import com.tawajood.vetclinic.databinding.FragmentAnimalInfoBinding
import com.tawajood.vetclinic.pojo.PreviousRequests
import com.tawajood.vetclinic.pojo.Vaccination
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class PreviousAnimalInfoFragment : Fragment(R.layout.fragment_animal_info) {


    private lateinit var binding: FragmentAnimalInfoBinding
    private lateinit var parent: MainActivity
    private val viewModel: AnimalViewModel by viewModels()
    private var id by Delegates.notNull<String>()
    private var type by Delegates.notNull<String>()

    private lateinit var reports: MutableList<PreviousRequests>
    private lateinit var vaccination: MutableList<Vaccination>
    private lateinit var vaAdapter: VaccinationAdapter
    private lateinit var medAdapter: MedicineAdapter
    private lateinit var reportsAdapter: PreviousReportsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnimalInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        id = requireArguments().getString(Constants.PET_ID).toString()
        type = requireArguments().getString(Constants.TYPE).toString()

        if (type == "1") {
            viewModel.getPreviousAnimalInfo(id)
        } else if (type == "2") {
            viewModel.getNewAnimalInfo(id)
        }

        setupUI()
        observeData()
        onClick()

        setupRecyclers()
    }

    private fun setupRecyclers() {
        vaAdapter = VaccinationAdapter()
        binding.rvVaccinations.adapter = vaAdapter

        medAdapter = MedicineAdapter()
        binding.rvMedicines.adapter = medAdapter

        reportsAdapter = PreviousReportsAdapter()
        binding.rvReports.adapter = reportsAdapter
    }

    private fun onClick() {
        binding.btnOk.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.previousAnimalInfo.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        //ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        val animal = it.data!!.animalInfo

                        Glide.with(requireContext()).load(animal.image).into(binding.ivPet)
                        binding.tvName.text = animal.name
                        binding.tvAge.text = animal.age.toString()
                        binding.tvGender.text = animal.gender
                        binding.tvType.text = animal.type
                        binding.tvWeight.text = animal.weight.toString()

                        vaAdapter.vaccination = animal.vaccinations
                        medAdapter.previousRequests = animal.previous_requests
                        reportsAdapter.previousRequests = animal.previous_requests

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newAnimalInfo.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        //ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        val animal = it.data!!.animalInfo

                        Glide.with(requireContext()).load(animal.image).into(binding.ivPet)
                        binding.tvName.text = animal.name
                        binding.tvAge.text = animal.age.toString()
                        binding.tvGender.text = animal.gender
                        binding.tvType.text = animal.type
                        binding.tvWeight.text = animal.weight.toString()

                        vaAdapter.vaccination = animal.vaccinations
                        medAdapter.previousRequests = animal.previous_requests
                        reportsAdapter.previousRequests = animal.previous_requests

                    }
                }
            }
        }
    }

    private fun setupUI() {

        parent.setTitle(getString(R.string.pet_info))
        parent.showBottomNav(false)
    }
}