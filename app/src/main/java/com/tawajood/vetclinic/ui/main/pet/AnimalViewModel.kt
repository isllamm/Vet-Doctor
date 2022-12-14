package com.tawajood.vetclinic.ui.main.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.AnimalInfoResponse
import com.tawajood.vetclinic.pojo.NotificationResponse
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _previousAnimalInfo =
        MutableStateFlow<Resource<AnimalInfoResponse>>(Resource.Idle())
    val previousAnimalInfo = _previousAnimalInfo.asSharedFlow()

    private val _newAnimalInfo =
        MutableStateFlow<Resource<AnimalInfoResponse>>(Resource.Idle())
    val newAnimalInfo = _newAnimalInfo.asSharedFlow()

    init {

    }

    fun getPreviousAnimalInfo(id: String) = viewModelScope.launch {
        try {
            _previousAnimalInfo.emit(Resource.Loading())
            val response = handleResponse(repository.getPreviousAnimalInfo(id))
            if (response.status) {
                _previousAnimalInfo.emit(Resource.Success(response.data!!))
            } else {
                _previousAnimalInfo.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _previousAnimalInfo.emit(Resource.Error(message = e.message!!))
        }
    }

    fun getNewAnimalInfo(id: String) = viewModelScope.launch {
        try {
            _newAnimalInfo.emit(Resource.Loading())
            val response = handleResponse(repository.getNewAnimalInfo(id))
            if (response.status) {
                _newAnimalInfo.emit(Resource.Success(response.data!!))
            } else {
                _newAnimalInfo.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _newAnimalInfo.emit(Resource.Error(message = e.message!!))
        }
    }
}