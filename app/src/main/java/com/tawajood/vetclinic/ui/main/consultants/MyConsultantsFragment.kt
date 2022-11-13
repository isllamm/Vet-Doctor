package com.tawajood.vetclinic.ui.main.consultants

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.CurrentConsultAdapter
import com.tawajood.vetclinic.adapters.NewConsultAdapter
import com.tawajood.vetclinic.adapters.NotificationAdapter
import com.tawajood.vetclinic.adapters.PreviousConsultAdapter
import com.tawajood.vetclinic.databinding.FragmentMyConsultantsBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyConsultantsFragment : Fragment(R.layout.fragment_my_consultants) {


    private lateinit var binding: FragmentMyConsultantsBinding
    private lateinit var parent: MainActivity
    private val viewModel: MyConsultantsViewModel by viewModels()
    private lateinit var previousConsultAdapter: PreviousConsultAdapter
    private lateinit var currentConsultAdapter: CurrentConsultAdapter
    private lateinit var newConsultAdapter: NewConsultAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyConsultantsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()
        onClick()
        setupPreviousConsult()
        setupCurrentConsult()
        setupNewConsult()

    }

    private fun setupNewConsult() {
        newConsultAdapter = NewConsultAdapter(object : NewConsultAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {

            }
        })
        binding.rvNew.adapter = newConsultAdapter
    }

    private fun setupCurrentConsult() {
        currentConsultAdapter = CurrentConsultAdapter(object : CurrentConsultAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {

            }
        })
        binding.rvCurrent.adapter = currentConsultAdapter
    }

    private fun setupPreviousConsult() {
        previousConsultAdapter =
            PreviousConsultAdapter(object : PreviousConsultAdapter.OnItemClick {
                override fun onItemClickListener(position: Int) {

                }

            })

        binding.rvPrevious.adapter = previousConsultAdapter
    }

    private fun onClick() {
        binding.currentFrame.setOnClickListener {
            binding.currentFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16_blue)
            binding.tvCurrent.setTextColor(Color.parseColor("#FFFFFFFF"))

            binding.newCFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16)
            binding.tvNewC.setTextColor(Color.parseColor("#9BDAE3"))

            binding.previousFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16)
            binding.tvPrevious.setTextColor(Color.parseColor("#9BDAE3"))

            binding.rvPrevious.isVisible = false
            binding.rvCurrent.isVisible = true
            binding.rvNew.isVisible = false

        }

        binding.newCFrame.setOnClickListener {
            binding.newCFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16_blue)
            binding.tvNewC.setTextColor(Color.parseColor("#FFFFFFFF"))

            binding.currentFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16)
            binding.tvCurrent.setTextColor(Color.parseColor("#9BDAE3"))

            binding.previousFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16)
            binding.tvPrevious.setTextColor(Color.parseColor("#9BDAE3"))

            binding.rvPrevious.isVisible = false
            binding.rvCurrent.isVisible = false
            binding.rvNew.isVisible = true

        }

        binding.previousFrame.setOnClickListener {
            binding.previousFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16_blue)
            binding.tvPrevious.setTextColor(Color.parseColor("#FFFFFFFF"))

            binding.newCFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16)
            binding.tvNewC.setTextColor(Color.parseColor("#9BDAE3"))

            binding.currentFrame.background =
                requireContext().getDrawable(R.drawable.full_border_radius_16)
            binding.tvCurrent.setTextColor(Color.parseColor("#9BDAE3"))

            binding.rvPrevious.isVisible = true
            binding.rvCurrent.isVisible = false
            binding.rvNew.isVisible = false
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.previousConsultants.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        previousConsultAdapter.consultants = it.data!!.previousConsultants.data
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentConsultants.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        currentConsultAdapter.consultants = it.data!!.currentConsultants.data
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newConsultants.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        newConsultAdapter.consultants = it.data!!.newConsultants.data
                    }
                }
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.the_consultants))
        parent.showBottomNav(true)
    }


}