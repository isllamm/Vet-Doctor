package com.tawajood.vetclinic.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.ContactUsResponse
import com.tawajood.vetclinic.pojo.EditProfileResponse
import com.tawajood.vetclinic.pojo.ProfileResponse
import com.tawajood.vetclinic.pojo.UpdatedBody
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _profile = MutableStateFlow<Resource<ProfileResponse>>(Resource.Idle())
    val profile = _profile.asSharedFlow()

    private val _updateProfile = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val updateProfile = _updateProfile.asSharedFlow()

    private val _editProfile = MutableStateFlow<Resource<EditProfileResponse>>(Resource.Idle())
    val editProfile = _editProfile.asSharedFlow()

    private val _deleteAccount = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val deleteAccount = _deleteAccount.asSharedFlow()

    private val _addClinicTimes = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addClinicTimes = _addClinicTimes.asSharedFlow()

    private val _deleteClinicTimes = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val deleteClinicTimes = _deleteClinicTimes.asSharedFlow()

    init {
        getProfile()
        getEditProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        try {
            _profile.emit(Resource.Loading())
            val response = handleResponse(repository.getProfile())
            if (response.status) {
                _profile.emit(Resource.Success(response.data!!))
            } else {
                _profile.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _profile.emit(Resource.Error(message = e.message!!))
        }
    }

    private fun getEditProfile() = viewModelScope.launch {
        try {
            _editProfile.emit(Resource.Loading())
            val response = handleResponse(repository.getEditProfile())
            if (response.status) {
                _editProfile.emit(Resource.Success(response.data!!))
            } else {
                _editProfile.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _editProfile.emit(Resource.Error(message = e.message!!))
        }
    }

    fun updateProfile(updatedBody: UpdatedBody) = viewModelScope.launch {
        try {
            _updateProfile.emit(Resource.Loading())
            val response = handleResponse(repository.updateProfile(updatedBody))
            if (response.status) {
                _updateProfile.emit(Resource.Success(response.data!!))
            } else {
                _updateProfile.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _updateProfile.emit(Resource.Error(message = e.message!!))
        }
    }

    fun deleteAccount() = viewModelScope.launch {
        try {
            _deleteAccount.emit(Resource.Loading())
            val response = handleResponse(repository.deleteAccount())
            if (response.status) {
                _deleteAccount.emit(Resource.Success(response.data!!))
            } else {
                _deleteAccount.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _deleteAccount.emit(Resource.Error(message = e.message!!))
        }
    }

    fun addClinicTimes(day: String, from: String, to: String) = viewModelScope.launch {
        try {
            _addClinicTimes.emit(Resource.Loading())
            val response = handleResponse(repository.addClinicTimes(day, from, to))
            if (response.status) {
                _addClinicTimes.emit(Resource.Success(response.data!!))
            } else {
                _addClinicTimes.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _addClinicTimes.emit(Resource.Error(message = e.message!!))
        }
    }

    fun deleteClinicTimes(timeId: String) = viewModelScope.launch {
        try {
            _deleteClinicTimes.emit(Resource.Loading())
            val response = handleResponse(repository.deleteClinicTimes(timeId))
            if (response.status) {
                _deleteClinicTimes.emit(Resource.Success(response.data!!))
            } else {
                _deleteClinicTimes.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _deleteClinicTimes.emit(Resource.Error(message = e.message!!))
        }
    }
}