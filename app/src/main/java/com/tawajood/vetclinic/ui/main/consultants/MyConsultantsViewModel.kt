package com.tawajood.vetclinic.ui.main.consultants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.*
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyConsultantsViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _previousConsultants =
        MutableStateFlow<Resource<PreviousConsultantsResponse>>(Resource.Idle())
    val previousConsultants = _previousConsultants.asSharedFlow()

    private val _previousConsultantInfo =
        MutableStateFlow<Resource<PreviousConsultantInfoResponse>>(Resource.Idle())
    val previousConsultantInfo = _previousConsultantInfo.asSharedFlow()

    private val _currentConsultants =
        MutableStateFlow<Resource<CurrentConsultantsResponse>>(Resource.Idle())
    val currentConsultants = _currentConsultants.asSharedFlow()

    private val _currentConsultantInfo =
        MutableStateFlow<Resource<CurrentConsultantInfoResponse>>(Resource.Idle())
    val currentConsultantInfo = _currentConsultantInfo.asSharedFlow()

    private val _newConsultants =
        MutableStateFlow<Resource<NewConsultantsResponse>>(Resource.Idle())
    val newConsultants = _newConsultants.asSharedFlow()

    private val _newConsultantInfo =
        MutableStateFlow<Resource<NewConsultantInfoResponse>>(Resource.Idle())
    val newConsultantInfo = _newConsultantInfo.asSharedFlow()

    private val _acceptedConsultant =
        MutableStateFlow<Resource<Any>>(Resource.Idle())
    val acceptedConsultant = _acceptedConsultant.asSharedFlow()

    private val _rejectedConsultant =
        MutableStateFlow<Resource<Any>>(Resource.Idle())
    val rejectedConsultant = _rejectedConsultant.asSharedFlow()

    init {
        getPreviousConsultants()
        getCurrentConsultants()
        getNewConsultants()
    }

    private fun getPreviousConsultants() = viewModelScope.launch {
        try {
            _previousConsultants.emit(Resource.Loading())
            val response = handleResponse(repository.getPreviousConsultants())
            if (response.status) {
                _previousConsultants.emit(Resource.Success(response.data!!))
            } else {
                _previousConsultants.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _previousConsultants.emit(Resource.Error(message = e.message!!))
        }
    }

    private fun getCurrentConsultants() = viewModelScope.launch {
        try {
            _currentConsultants.emit(Resource.Loading())
            val response = handleResponse(repository.getCurrentConsultants())
            if (response.status) {
                _currentConsultants.emit(Resource.Success(response.data!!))
            } else {
                _currentConsultants.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _currentConsultants.emit(Resource.Error(message = e.message!!))
        }
    }

    private fun getNewConsultants() = viewModelScope.launch {
        try {
            _newConsultants.emit(Resource.Loading())
            val response = handleResponse(repository.getNewConsultants())
            if (response.status) {
                _newConsultants.emit(Resource.Success(response.data!!))
            } else {
                _newConsultants.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            Log.d("islam", "getNewConsultants: ${e.message}")
            _newConsultants.emit(Resource.Error(message = e.message!!))
        }
    }

    fun rejectedConsultants(id: String) = viewModelScope.launch {
        try {
            _rejectedConsultant.emit(Resource.Loading())
            val response = handleResponse(repository.rejectedConsultants(id))
            if (response.status) {
                _rejectedConsultant.emit(Resource.Success(response.data!!))
            } else {
                _rejectedConsultant.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _rejectedConsultant.emit(Resource.Error(message = e.message!!))
        }
    }

    fun acceptedConsultants(id: String) = viewModelScope.launch {
        try {
            _acceptedConsultant.emit(Resource.Loading())
            val response = handleResponse(repository.acceptedConsultants(id))
            if (response.status) {
                _acceptedConsultant.emit(Resource.Success(response.data!!))
            } else {
                _acceptedConsultant.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _acceptedConsultant.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getPreviousConsultantsInfo(id: String) = viewModelScope.launch {
        try {
            _previousConsultantInfo.emit(Resource.Loading())
            val response = handleResponse(repository.getPreviousConsultantsInfo(id))
            if (response.status) {
                _previousConsultantInfo.emit(Resource.Success(response.data!!))
            } else {
                _previousConsultantInfo.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _previousConsultantInfo.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getNewConsultantsInfo(id: String) = viewModelScope.launch {
        try {
            _newConsultantInfo.emit(Resource.Loading())
            val response = handleResponse(repository.getNewConsultantsInfo(id))
            if (response.status) {
                _newConsultantInfo.emit(Resource.Success(response.data!!))
            } else {
                _newConsultantInfo.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _newConsultantInfo.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getCurrentConsultantsInfo(id: String) = viewModelScope.launch {
        try {
            _currentConsultantInfo.emit(Resource.Loading())
            val response = handleResponse(repository.getCurrentConsultantsInfo(id))
            if (response.status) {
                _currentConsultantInfo.emit(Resource.Success(response.data!!))
            } else {
                _currentConsultantInfo.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _currentConsultantInfo.emit(Resource.Error(message = e.message!!))
        }
    }

}