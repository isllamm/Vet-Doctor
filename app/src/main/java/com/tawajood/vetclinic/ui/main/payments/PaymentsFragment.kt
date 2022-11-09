package com.tawajood.vetclinic.ui.main.payments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentAboutUsBinding
import com.tawajood.vetclinic.databinding.FragmentPaymentsBinding
import com.tawajood.vetclinic.pojo.Bank
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.ui.main.about_us.AboutUsViewModel
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payments.view.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PaymentsFragment : Fragment(R.layout.fragment_payments) {


    private lateinit var binding: FragmentPaymentsBinding
    private lateinit var parent: MainActivity
    private val viewModel: PaymentsViewModel by viewModels()
    private lateinit var banksAdapter: ArrayAdapter<String>
    private var banks = mutableListOf<Bank>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        onClick()
        observeData()
        setupSpeBanks()
    }

    private fun onClick() {
        binding.btnWithdraw.setOnClickListener {
            viewModel.withdrawMoney(
                binding.etAmount.text.toString(),
                binding.etName.text.toString(),
                banks[binding.typeBank.selectedItemPosition].id.toString(),
                binding.etAccountNum.text.toString()
            )
        }
    }

    private fun setupSpeBanks() {
        banksAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.typeBank.adapter = banksAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.money.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        val data = it.data!!.clinic
                        binding.withdraw.text = data.valid_balance.toString()
                        binding.pending.text = data.suspended_balance.toString()

                        banks = it.data.banks
                        banks.forEach { bank ->
                            banksAdapter.add(bank.name)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.withdraw.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                    }
                }
            }
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.payments))
    }


}