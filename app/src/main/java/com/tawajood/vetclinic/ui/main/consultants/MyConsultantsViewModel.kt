package com.tawajood.vetclinic.ui.main.consultants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.CurrentConsultantsResponse
import com.tawajood.vetclinic.pojo.NewConsultantsResponse
import com.tawajood.vetclinic.pojo.NotificationResponse
import com.tawajood.vetclinic.pojo.PreviousConsultantsResponse
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

    private val _currentConsultants =
        MutableStateFlow<Resource<CurrentConsultantsResponse>>(Resource.Idle())
    val currentConsultants = _currentConsultants.asSharedFlow()


    private val _newConsultants =
        MutableStateFlow<Resource<NewConsultantsResponse>>(Resource.Idle())
    val newConsultants = _newConsultants.asSharedFlow()

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

}