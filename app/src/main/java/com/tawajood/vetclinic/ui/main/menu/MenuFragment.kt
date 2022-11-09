package com.tawajood.vetclinic.ui.main.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.FragmentLoginBinding
import com.tawajood.vetclinic.databinding.FragmentMenuBinding
import com.tawajood.vetclinic.ui.auth.AuthActivity
import com.tawajood.vetclinic.ui.auth.AuthViewModel
import com.tawajood.vetclinic.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu) {


    private lateinit var binding: FragmentMenuBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        onClick()
        observeData()
    }

    private fun observeData() {

    }

    private fun onClick() {
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
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.menu))
    }


}