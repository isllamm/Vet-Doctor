package com.tawajood.vetclinic.ui.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.ProfileResponse
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _menuInfo = MutableStateFlow<Resource<ProfileResponse>>(Resource.Idle())
    val menuInfo = _menuInfo.asSharedFlow()

    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        try {
            _menuInfo.emit(Resource.Loading())
            val response = handleResponse(repository.getProfile())
            if (response.status) {
                _menuInfo.emit(Resource.Success(response.data!!))
            } else {
                _menuInfo.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _menuInfo.emit(Resource.Error(message = e.message!!))
        }
    }
}