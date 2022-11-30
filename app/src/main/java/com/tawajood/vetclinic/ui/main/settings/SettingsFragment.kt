package com.tawajood.vetclinic.ui.main.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.data.PrefsHelper
import com.tawajood.vetclinic.databinding.FragmentSettingsBinding
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.updateLanguage


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var parent: MainActivity
    private var isAr = true
    private var isON = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        if (PrefsHelper.getLanguage() == Constants.AR) {
            binding.lang.text = "English"
        } else {
            binding.lang.text = "العربية"
        }

        setupUI()
        onClick()
    }

    private fun onClick() {
        binding.cardLang.setOnClickListener {

            if (PrefsHelper.getLanguage() == Constants.AR) {
                PrefsHelper.setLanguage(Constants.EN)
            } else {
                PrefsHelper.setLanguage(Constants.AR)
            }
            updateLang()
        }
    }

    private fun setupUI() {
        parent.setTitle(getString(R.string.change_language))
        parent.showBottomNav(false)
    }

    private fun updateLang() {
        updateLanguage(parent.applicationContext)
        parent.finish()
        val intent = Intent(parent, MainActivity::class.java)
        startActivity(intent)
    }
}