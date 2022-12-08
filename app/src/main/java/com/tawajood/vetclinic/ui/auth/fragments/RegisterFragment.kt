package com.tawajood.vetclinic.ui.auth.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.data.PrefsHelper
import com.tawajood.vetclinic.databinding.FragmentRegisterBinding
import com.tawajood.vetclinic.pojo.RegisterBody
import com.tawajood.vetclinic.ui.auth.AuthActivity
import com.tawajood.vetclinic.ui.auth.AuthViewModel
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import com.tawajood.vetclinic.utils.getAddressForTextView
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {


    private lateinit var binding: FragmentRegisterBinding
    private lateinit var parent: AuthActivity
    private lateinit var registerBody: RegisterBody
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLatLng: LatLng
    private var lat: Double? = null
    private var lng: Double? = null
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(requireView())
        parent = requireActivity() as AuthActivity
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        setupUI()
        onClick()
        observeData()
    }

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            getCurrentLocation()
        } else {
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClick() {
        binding.ivLoc.setOnClickListener {
            if (!SmartLocation.with(requireContext()).location().state()
                    .locationServicesEnabled()
            ) {
                ToastUtils.showToast(
                    requireContext(),
                    getString(R.string.location),
                )
            } else {
                parent.showLoading()
                locationPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }


        binding.btnCheck.setOnClickListener {

            if (validate()) {
                parent.navController.navigate(
                    R.id.OTPFragment,
                    bundleOf(
                        Constants.USERNAME to binding.clinicNameEt.text.toString(),
                        Constants.PHONE to binding.phoneEt.text.toString(),
                        Constants.LOGIN to 1,
                        Constants.COUNTRY_CODE to "+" + binding.ccp.selectedCountryCode.toString(),
                        Constants.EMAIL to binding.emailEt.text.toString(),
                        Constants.ADDRESS to binding.addressEt.text.toString(),
                        Constants.REG_NUM to binding.licenseNumEt.text.toString(),
                    )
                )


            }

        }
    }

    private fun setupUI() {
        parent.imInOTP(false, "")
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.clinicNameEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.name_is_required))

            return false
        }

        if (TextUtils.isEmpty(binding.phoneEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.phone_is_required))

            return false
        }



        if (TextUtils.isEmpty(binding.emailEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.email_required))

            return false
        }
        if (TextUtils.isEmpty(binding.licenseNumEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.license_required))

            return false
        }
        if (TextUtils.isEmpty(binding.addressEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.address_required))

            return false
        }



        if (!binding.switch1.isChecked) {
            return false
        }




        return true
    }

    private fun observeData() {

    }

    private fun getCurrentLocation() {
        parent.showLoading()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnCompleteListener {
                parent.hideLoading()
                it.addOnSuccessListener { location ->
                    if (location != null) {
                        currentLatLng = LatLng(location.latitude, location.longitude)
                        lat = location.latitude
                        lng = location.longitude

                        getAddressForTextView(
                            requireContext(),
                            location.latitude,
                            location.longitude,
                            binding.addressEt
                        )
                    } else {
                        getCurrentLocation()
                    }
                }
                it.addOnFailureListener { e ->
                    Log.d("islam", "getLastKnownLocation: ${e.message}")
                }
            }
    }
}