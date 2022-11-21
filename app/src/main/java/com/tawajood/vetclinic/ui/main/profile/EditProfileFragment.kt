package com.tawajood.vetclinic.ui.main.profile

import ResultDialogFragment
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.adapters.*
import com.tawajood.vetclinic.databinding.FragmentEditProfileBinding
import com.tawajood.vetclinic.databinding.FragmentProfileBinding
import com.tawajood.vetclinic.pojo.*
import com.tawajood.vetclinic.ui.main.MainActivity
import com.tawajood.vetclinic.utils.Constants
import com.tawajood.vetclinic.utils.Resource
import com.tawajood.vetclinic.utils.getAddressForTextView
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {


    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var parent: MainActivity
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var daysAdapter: ArrayAdapter<String>
    private var days = mutableListOf<Day>()
    private lateinit var speAdapter: ArrayAdapter<String>
    private var specializations = mutableSetOf<Specialization>()
    private var specializationsTemp = mutableListOf<Specialization>()
    private var specializationNames = mutableListOf<SpecializationName>()
    private lateinit var editSpecializationAdapter: EditSpecializationAdapter
    private lateinit var editTimeAdapter: EditTimeAdapter
    private var times = mutableListOf<ClinicDay>()
    private var image: File? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLatLng: LatLng

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        viewModel.getEditProfile()
        setupUI()
        onClick()
        observeData()
        setupDays()
        setupSpecializations()
        setupSpecializationsEdit()
        setupTimes()
    }

    private fun setupTimes() {
        editTimeAdapter = EditTimeAdapter(object : EditTimeAdapter.OnDeleteClickListener {
            override fun onDeleteClickListener(position: Int) {
                viewModel.deleteClinicTimes(times[position].times[0].id.toString())
            }

        })
        if (times.isNotEmpty()) {
            binding.rvTimes.isVisible = true
        }
        editTimeAdapter.setTime(times)
        binding.rvTimes.adapter = editTimeAdapter
    }

    private fun setupSpecializationsEdit() {
        editSpecializationAdapter =
            EditSpecializationAdapter(object : EditSpecializationAdapter.OnDeleteClickListener {
                override fun onDeleteClickListener(position: Int) {
                    specializations.remove(specializations.elementAt(position))
                    editSpecializationAdapter.setSpecialization(specializations)

                    if (specializations.isEmpty()) {
                        binding.rvSpecializations.isVisible = false
                    }
                }

            })
        if (specializations.isNotEmpty()) {
            binding.rvSpecializations.isVisible = true
        }
        editSpecializationAdapter.setSpecialization(specializations)
        binding.rvSpecializations.adapter = editSpecializationAdapter
    }

    private fun onClick() {
        binding.btnAddTime.setOnClickListener {
            viewModel.addClinicTimes(
                days[binding.timingsSpinner.selectedItemPosition].id.toString(),
                binding.fromEt.text.toString(),
                binding.toEt.text.toString(),
            )
        }

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
                Log.d("islam", "onClick: Hi")
            }
        }
        binding.ivEdit.setOnClickListener {
            imagePermissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }
        binding.btnEdit.setOnClickListener {
            viewModel.updateProfile(
                UpdatedBody(
                    binding.clinicNameEt.text.toString(),
                    binding.phoneEt.text.toString(),
                    binding.ccp.selectedCountryCode.toString(),
                    binding.emailEt.text.toString(),
                    binding.licenseNumEt.text.toString(),
                    binding.feesEt.text.toString(),
                    binding.addressEt.text.toString(),
                    binding.detailsEt.text.toString(),
                    1,
                    PrefsHelper.getCurrentLat().toString(),
                    PrefsHelper.getCurrentLng().toString(),
                    image,
                    editSpecializationAdapter.getSet(),
                )
            )
        }

        binding.specializationsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {


                    if (specializationsTemp.isNotEmpty()) {

                        specializations.add(specializationsTemp[position])
                        editSpecializationAdapter.setSpecialization(specializations)
                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }

    private fun setupDays() {
        daysAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.timingsSpinner.adapter = daysAdapter
    }

    private fun setupSpecializations() {
        speAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.specializationsSpinner.adapter = speAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.editProfile.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        val profile = it.data!!.profile
                        Glide.with(requireContext()).load(profile.image).into(binding.imgClinic)
                        binding.tvName.text = profile.name
                        binding.clinicNameEt.setText(profile.name)
                        binding.emailEt.setText(profile.email)
                        binding.phoneEt.setText(profile.phone)
                        binding.licenseNumEt.setText(profile.registration_number)
                        binding.addressEt.setText(profile.address)
                        binding.feesEt.setText(profile.consultation_fees.toString())
                        binding.detailsEt.setText(profile.details)
                        specializationNames = it.data.specializations
                        specializationNames.forEach { specializationNames ->
                            speAdapter.add(specializationNames.name)
                        }

                        days = it.data.days
                        days.forEach { days ->
                            daysAdapter.add(days.name)
                        }

                        specializations =
                            it.data.profile.specializations.toSet() as MutableSet<Specialization>
                        specializationsTemp = it.data.profile.specializations

                        editSpecializationAdapter.specializations = specializations

                        times = it.data.profile.clinic_days
                        editTimeAdapter.clinicDays = times

                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.updateProfile.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {

                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {

                        PrefsHelper.setUsername(binding.clinicNameEt.text.toString())
                        PrefsHelper.setEmail(binding.emailEt.text.toString())
                        PrefsHelper.setPhone(binding.phoneEt.text.toString())
                        PrefsHelper.setCountryCode(binding.ccp.selectedCountryCode.toString())
                        ResultDialogFragment.newInstance(
                            getString(R.string.profile_updated_successfully), R.drawable.done
                        ).show(
                            parentFragmentManager,
                            ResultDialogFragment::class.java.canonicalName
                        )
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addClinicTimes.collectLatest {
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
                            getString(R.string.time_added_successfully), R.drawable.done
                        ).show(
                            parentFragmentManager,
                            ResultDialogFragment::class.java.canonicalName
                        )
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.deleteClinicTimes.collectLatest {
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
                            getString(R.string.time_deleted_successfully), R.drawable.done
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
        parent.showBottomNav(false)
        parent.setTitle(getString(R.string.profile))
    }

    private val imagePermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    pickImageResultLauncher.launch(intent)
                }
        } else {
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            ToastUtils.showToast(requireContext(), "Permission Denied")
        }
    }

    private fun getRealPathFromURI(activity: Activity, contentURI: Uri): String {
        val result: String
        val cursor: Cursor? = activity.contentResolver
            .query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path.toString()
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(Constants._DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    private var pickImageResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            val resultCode = result.resultCode
            val data = result.data!!

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val file = File(getRealPathFromURI(parent, data.data!!))
                    image = file
                    Log.d("islam", ": ${image!!.name}")
                    Log.d("islam", ": ${image!!.path}")

                    Glide.with(requireContext())
                        .load(file)
                        .into(binding.imgClinic)

                }
                ImagePicker.RESULT_ERROR -> {
                    ToastUtils.showToast(requireContext(), ImagePicker.getError(data))
                }
                else -> {
                    ToastUtils.showToast(requireContext(), "Task Cancelled")
                }
            }
        }

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        Log.d("islam", "Request Per: Hi")
        if (result) {
            getCurrentLocation()
        } else {
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
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
                        PrefsHelper.setCurrentLat(location.latitude.toFloat())
                        PrefsHelper.setCurrentLng(location.longitude.toFloat())

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