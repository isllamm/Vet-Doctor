package com.tawajood.vetclinic.ui.main.reports

import ResultDialogFragment
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.BodyPartsAdapter
import com.tawajood.vetclinic.adapters.NotificationAdapter
import com.tawajood.vetclinic.databinding.FragmentAddReportBinding
import com.tawajood.vetclinic.databinding.FragmentNotificationsBinding
import com.tawajood.vetclinic.pojo.BodyPart
import com.tawajood.vetclinic.pojo.ReportBody
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.notifications.NotificationsViewModel
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddReportFragment : Fragment(R.layout.fragment_add_report) {


    private lateinit var binding: FragmentAddReportBinding
    private lateinit var parent: MainActivity
    private val viewModel: AddReportViewModel by viewModels()
    private lateinit var bodyPartsAdapter: BodyPartsAdapter
    private var bodyPart = mutableListOf<BodyPart>()
    private var id by Delegates.notNull<String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddReportBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        id = requireArguments().getString(Constants.CONSULTANT_ID).toString()

        setupUI()
        observeData()
        setupBodyParts()
        onClick()
    }

    private fun onClick() {
        binding.btnAdd.setOnClickListener {
            if (validate()) {
                viewModel.addReport(
                    ReportBody(
                        id.toInt(),
                        binding.etReport.text.toString(),
                        binding.etMedicine.text.toString(),
                        bodyPartsAdapter.getIds()
                    )
                )
            }

        }
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.etReport.text)) {
            ToastUtils.showToast(requireContext(), "التقرير مطلوب")

            return false
        }

        if (TextUtils.isEmpty(binding.etMedicine.text)) {

            ToastUtils.showToast(requireContext(), "الوصفات مطلوبة")

            return false
        }

        return true
    }

    private fun setupBodyParts() {
        bodyPartsAdapter = BodyPartsAdapter()

        binding.rvBodyParts.adapter = bodyPartsAdapter
    }

    private fun setupUI() {
        parent.showBottomNav(false)
        parent.setTitle(getString(R.string.add_report))
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.bodyParts.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        bodyPartsAdapter.bodyParts = it.data!!.bodyParts

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addReport.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        ToastUtils.showToast(requireContext(), getString(R.string.report_added))
                        parent.navController.navigate(R.id.myConsultantsFragment)

                        /*ResultDialogFragment.newInstance(
                            getString(R.string.report_added), R.drawable.done
                        ).show(
                            parentFragmentManager,
                            ResultDialogFragment::class.java.canonicalName
                        )*/
                    }
                }
            }
        }
    }


}